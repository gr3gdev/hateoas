package com.example.hateoas;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.lang.reflect.Method;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.config.HypermediaWebTestClientConfigurer;
import org.springframework.hateoas.server.core.TypeReferences;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import com.example.hateoas.dao.bean.Avatar;
import com.example.hateoas.dao.bean.User;
import com.example.hateoas.dao.repository.AvatarRepository;
import com.example.hateoas.dao.repository.UserRepository;
import com.example.hateoas.domain.model.AbstractModel;
import com.example.hateoas.utils.ImageUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT, classes = Application.class)
@AutoConfigureWebTestClient
@TestMethodOrder(OrderAnnotation.class)
abstract class AbstractApplicationTests<M extends AbstractModel> {

	@Autowired
	protected UserRepository userRepository;

	@Autowired
	protected AvatarRepository avatarRepository;

	@LocalServerPort
	private int port;

	protected WebTestClient client;

	private byte[] loadImage(String path) throws IOException {
		return ImageUtils.optimizeImage(AbstractApplicationTests.class.getResourceAsStream(path).readAllBytes());
	}

	@BeforeEach
	public void initJDD(@Autowired HypermediaWebTestClientConfigurer configurer) throws IOException {
		final Avatar avatar1 = new Avatar();
		avatar1.setId(1L);
		avatar1.setData(loadImage("/avatars/avatar1.jpg"));
		avatarRepository.save(avatar1);

		final Avatar avatar2 = new Avatar();
		avatar2.setId(2L);
		avatar2.setData(loadImage("/avatars/avatar2.jpeg"));
		avatarRepository.save(avatar2);

		final User user1 = new User();
		user1.setId(1L);
		user1.setName("Mickaël TOTO");
		user1.setAvatar(avatar1);
		userRepository.save(user1);

		final User user2 = new User();
		user2.setId(2L);
		user2.setName("Bradley PASLONG");
		userRepository.save(user2);

		final User user3 = new User();
		user3.setId(3L);
		user3.setName("Miou WAF");
		user3.setAvatar(avatar2);
		userRepository.save(user3);

		user1.getContacts().add(user2);
		userRepository.save(user1);

		final String baseURI = "http://localhost:" + this.port + "/api";
		client = WebTestClient.bindToServer()
				.baseUrl(baseURI)
				.build()
				.mutateWith(configurer);
	}

	protected void assertModel(TestInfo testInfo, String json) {
		final ObjectMapper mapper = new ObjectMapper();
		try {
			final String testMethodName = testInfo.getTestMethod().map(Method::getName).orElseThrow();
			final String expectedJson = new String(
					AbstractApplicationTests.class
							.getResourceAsStream("/api/" + name() + "/" + testMethodName + ".json")
							.readAllBytes());
			assertEquals(mapper.readTree(expectedJson), mapper.readTree(json));
		} catch (IOException e) {
			fail(e);
		}
	}

	protected abstract String name();

	protected abstract Class<M> getModelClass();

	protected abstract M newModel();

	protected abstract M updatedModel();

	private String path(Long id) {
		String path = "/" + name() + "/";
		if (id != null) {
			path += id.toString();
		}
		return path;
	}

	@Test
	@Order(1)
	void getAll(TestInfo testInfo) throws Exception {
		client.get().uri(path(null)).accept(MediaTypes.HAL_JSON)
				.exchange()
				.expectStatus().isOk()
				.expectBody(new TypeReferences.CollectionModelType<M>() {
				})
				.consumeWith(result -> {
					CollectionModel<M> model = result.getResponseBody();
					assertNotNull(model);
					assertModel(testInfo, new String(result.getResponseBodyContent()));
				});
	}

	@Test
	@Order(2)
	void get_NotFound(TestInfo testInfo) throws Exception {
		client.get().uri(path(999L)).accept(MediaTypes.HAL_JSON)
				.exchange()
				.expectStatus().isNotFound();
	}

	@Test
	@Order(2)
	void get(TestInfo testInfo) throws Exception {
		client.get().uri(path(1L)).accept(MediaTypes.HAL_JSON)
				.exchange()
				.expectStatus().isOk()
				.expectBody(getModelClass())
				.consumeWith(result -> {
					M model = result.getResponseBody();
					assertNotNull(model);
					assertModel(testInfo, new String(result.getResponseBodyContent()));
				});
	}

	@Test
	@Order(3)
	void post(TestInfo testInfo) throws Exception {
		client.post().uri(path(null)).accept(MediaTypes.HAL_JSON)
				.body(BodyInserters.fromValue(newModel()))
				.exchange()
				.expectStatus().isCreated()
				.expectBody(getModelClass())
				.consumeWith(result -> {
					final M model = result.getResponseBody();
					assertNotNull(model);
					assertModel(testInfo, new String(result.getResponseBodyContent()));
				});
	}

	@Test
	@Order(4)
	void delete() throws Exception {
		client.delete().uri(path(3L)).accept(MediaTypes.HAL_JSON)
				.exchange()
				.expectStatus().isNoContent();
	}

	@Test
	@Order(5)
	void put(TestInfo testInfo) throws Exception {
		client.put().uri(path(null)).accept(MediaTypes.HAL_JSON)
				.body(BodyInserters.fromValue(updatedModel()))
				.exchange()
				.expectStatus().isOk()
				.expectBody(getModelClass())
				.consumeWith(result -> {
					final M model = result.getResponseBody();
					assertNotNull(model);
					assertModel(testInfo, new String(result.getResponseBodyContent()));
				});
	}

}
