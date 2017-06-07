package com.antbean.springbootdemointegration;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.integration.dsl.GenericEndpointSpec;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.RouterSpec;
import org.springframework.integration.dsl.channel.MessageChannels;
import org.springframework.integration.dsl.core.Pollers;
import org.springframework.integration.dsl.file.Files;
import org.springframework.integration.dsl.mail.Mail;
import org.springframework.integration.dsl.support.Consumer;
import org.springframework.integration.dsl.support.Function;
import org.springframework.integration.dsl.support.PropertiesBuilder;
import org.springframework.integration.feed.inbound.FeedEntryMessageSource;
import org.springframework.integration.file.FileNameGenerator;
import org.springframework.integration.file.support.FileExistsMode;
import org.springframework.integration.mail.MailSendingMessageHandler;
import org.springframework.integration.router.MethodInvokingRouter;
import org.springframework.integration.scheduling.PollerMetadata;
import org.springframework.integration.transformer.GenericTransformer;
import org.springframework.messaging.Message;

import com.rometools.rome.feed.synd.SyndEntry;

@SpringBootApplication
public class SpringBootDemoIntegrationApplication {
	@Value("https://spring.io/blog.atom")
	Resource resource;

	@Bean(name = PollerMetadata.DEFAULT_POLLER)
	public PollerMetadata poller() {
		return Pollers.fixedRate(500).get();
	}

	@Bean
	public FeedEntryMessageSource feedMessageSource() throws IOException {
		FeedEntryMessageSource messageSource = new FeedEntryMessageSource(resource.getURL(), "news");
		return messageSource;
	}

	@Bean
	public IntegrationFlow myFlow() throws IOException {
		return IntegrationFlows.from(feedMessageSource()).route(new Function<SyndEntry, String>() {
			public String apply(SyndEntry t) {
				return t.getCategories().get(0).getName();
			}
		}, new Consumer<RouterSpec<String, MethodInvokingRouter>>() {
			@Override
			public void accept(RouterSpec<String, MethodInvokingRouter> t) {
				t.channelMapping("releases", "releasesChannel").channelMapping("engineering", "engineeringChannel")
						.channelMapping("news", "newsChannel");
			}
		}) //
				.get();

		// return IntegrationFlows.from(feedMessageSource()) // 4
		// .<SyndEntry, String> route(payload -> //
		// payload.getCategories().get(0).getName(), // 5
		// mapping -> mapping.channelMapping("releases", "releasesChannel") // 6
		// .channelMapping("engineering", "engineeringChannel") //
		// .channelMapping("news", "newsChannel")) //
		// .get();
	}

	@Bean
	public IntegrationFlow releasesFlow() {
		return IntegrationFlows.from(MessageChannels.queue("releasesChannel", 10))
				.transform(new GenericTransformer<SyndEntry, String>() {
					@Override
					public String transform(SyndEntry source) {
						return " <" + source.getTitle() + "> " + source.getLink()
								+ System.getProperty("line.separator");
					}
				}).handle(Files.outboundAdapter(new File("e:/springblog")).fileExistsMode(FileExistsMode.APPEND)
						.charset("UTF-8").fileNameGenerator(new FileNameGenerator() {
							@Override
							public String generateFileName(Message<?> message) {
								return "releases.txt";
							}
						}).get())
				.get();

		// return IntegrationFlows.from(MessageChannels.queue("releasesChannel",
		// 10)) // 1
		// .<SyndEntry, String> transform( //
		// payload -> " <" + payload.getTitle() + "> " + //
		// payload.getLink() + System.getProperty("line.separator")) // 2
		// .handle(Files.outboundAdapter(new File("e:/springblog")) // 3
		// .fileExistsMode(FileExistsMode.APPEND) //
		// .charset("UTF-8") //
		// .fileNameGenerator(message -> "releases.txt") //
		// .get()) //
		// .get();
	}

	@Bean
	public IntegrationFlow enginneringFlow() {
		return IntegrationFlows.from(MessageChannels.queue("engineeringChannel", 10))
				.transform(new GenericTransformer<SyndEntry, String>() {
					@Override
					public String transform(SyndEntry source) {
						return " <" + source.getTitle() + "> " + source.getLink()
								+ System.getProperty("line.separator");
					}
				}).handle(Files.outboundAdapter(new File("e:/springblog")).fileExistsMode(FileExistsMode.APPEND)
						.charset("UTF-8").fileNameGenerator(new FileNameGenerator() {
							@Override
							public String generateFileName(Message<?> message) {
								return "enginnering.txt";
							}
						}).get())
				.get();

		// return
		// IntegrationFlows.from(MessageChannels.queue("engineeringChannel",
		// 10)) // 1
		// .<SyndEntry, String> transform( //
		// e -> " <" + e.getTitle() + "> " + //
		// e.getLink() + System.getProperty("line.separator")) // 2
		// .handle(Files.outboundAdapter(new File("e:/springblog")) // 3
		// .fileExistsMode(FileExistsMode.APPEND) //
		// .charset("UTF-8") //
		// .fileNameGenerator(message -> "enginnering.txt") //
		// .get()) //
		// .get();
	}

	@Bean
	public IntegrationFlow newsFlow() {
		return IntegrationFlows.from(MessageChannels.queue("newsChannel", 10)) //
				.transform(new GenericTransformer<SyndEntry, String>() {
					@Override
					public String transform(SyndEntry source) {
						return " <" + source.getTitle() + "> " + source.getLink()
								+ System.getProperty("line.separator");
					}
				}).enrichHeaders(Mail.headers().subject("来自Spring的新闻").to("1902328305@qq.com").from("lengyucc@126.com"))
				.handle(Mail.outboundAdapter("smtp.126.com").port(25).protocol("smtp")
						.credentials("lengyucc@126.com", "abcd123")
						.javaMailProperties(new Consumer<PropertiesBuilder>() {
							@Override
							public void accept(PropertiesBuilder t) {
								t.put("mail.debug", "false");
							}

						}), new Consumer<GenericEndpointSpec<MailSendingMessageHandler>>() {
							@Override
							public void accept(GenericEndpointSpec<MailSendingMessageHandler> t) {
								t.id("smtpOut");
							}
						})
				.get();

		// return IntegrationFlows.from(MessageChannels.queue("newsChannel",
		// 10)) //
		// .<SyndEntry, String> transform( //
		// payload -> " <" + payload.getTitle() + "> " + //
		// payload.getLink() + System.getProperty("line.separator")) //
		// .enrichHeaders( // 1
		// Mail.headers() //
		// .subject("来自Spring的新闻") //
		// .to("2474445818@qq.com") //
		// .from("1902328305@qq.com")) //
		// .handle(Mail.outboundAdapter("smtp.126.com") // 2
		// .port(25) //
		// .protocol("smtp") //
		// .credentials("2474445818@qq.com", "**********") //
		// .javaMailProperties(p -> p.put("mail.debug", "false")), //
		// e -> e.id("smtpOut"))
		// .get();
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringBootDemoIntegrationApplication.class, args);
	}
}
