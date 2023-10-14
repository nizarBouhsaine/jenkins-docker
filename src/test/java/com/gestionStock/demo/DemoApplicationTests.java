package com.gestionStock.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gestionStock.demo.Model.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
class DemoApplicationTests {

	@Test
	void contextLoads() {
	}

	@Autowired
	private MockMvc mockMvc;

	Product productToCreate = new Product();
	{
		productToCreate.setId(4L);
		productToCreate.setName("Test Name");
		productToCreate.setDescription("Test Description");
		productToCreate.setPrice(200);
		productToCreate.setQuantity(5);
	}


	public static String asJsonString(final Object obj) {
		try {
			final ObjectMapper mapper = new ObjectMapper();
			final String jsonContent = mapper.writeValueAsString(obj);
			return jsonContent;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	@Test
	public void testGetAllProducts() throws Exception {
		String response = mockMvc.perform(MockMvcRequestBuilders.get("/api/products")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn()
				.getResponse()
				.getContentAsString();
		System.out.println(response);

	}
	@Test
	public void testCreateProduct() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders.post("/api/products")
						.contentType(MediaType.APPLICATION_JSON)
						.content(asJsonString(productToCreate)))
				.andExpect(MockMvcResultMatchers.status().isCreated());
	}
	@Test
	public void testGetProductById() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/api/products/{id}", productToCreate.getId())
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}
	@Test
	public void testUpdateProduct() throws Exception {
		productToCreate.setQuantity(50);
		mockMvc.perform(MockMvcRequestBuilders.put("/api/products/{id}", productToCreate.getId())
						.contentType(MediaType.APPLICATION_JSON)
						.content(asJsonString(productToCreate)))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

}
