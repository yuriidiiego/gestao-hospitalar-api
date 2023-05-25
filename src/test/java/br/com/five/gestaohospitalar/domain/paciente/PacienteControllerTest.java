package br.com.five.gestaohospitalar.domain.paciente;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import br.com.five.gestaohospitalar.GestaohospitalarApplication;
import br.com.five.gestaohospitalar.domain.dadopessoal.Sexo;
import br.com.five.gestaohospitalar.domain.paciente.payload.request.PacientePostRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest(
  classes = GestaohospitalarApplication.class,
  webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@AutoConfigureMockMvc(addFilters = false)
@Testcontainers
class PacienteControllerTest {

  @Container
  private static final PostgreSQLContainer<?> container = new PostgreSQLContainer<>(
    "postgres:latest"
  )
    .withDatabaseName("gestao-hospitalar-test")
    .withUsername("postgres")
    .withPassword("test");

  @DynamicPropertySource
  static void postgresqlProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", container::getJdbcUrl);
    registry.add("spring.datasource.username", container::getUsername);
    registry.add("spring.datasource.password", container::getPassword);
  }

  @Autowired
  private PacienteRepository pacienteRepository;

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @AfterEach
  public void tearDown() {
    pacienteRepository.deleteAll();
  }

  @Test
  void salvar_deveSalvarPaciente() throws Exception {
    PacientePostRequest pacientePostRequest = new PacientePostRequest();
    pacientePostRequest.setNome("João da Silva");
    pacientePostRequest.setCpf("70989098230");
    pacientePostRequest.setDataNascimento(LocalDate.of(1980, 2, 2));
    pacientePostRequest.setSexo(Sexo.MASCULINO);

    mockMvc
      .perform(
        post("/pacientes")
          .contentType(MediaType.APPLICATION_JSON)
          .content(objectMapper.writeValueAsString(pacientePostRequest))
      )
      .andExpect(status().isCreated())
      .andExpect(jsonPath("$.nome").value("João da Silva"))
      .andExpect(jsonPath("$.cpf").value("70989098230"))
      .andExpect(jsonPath("$.dataNascimento").value("02/02/1980"))
      .andExpect(jsonPath("$.sexo").value("MASCULINO"));
  }
}
