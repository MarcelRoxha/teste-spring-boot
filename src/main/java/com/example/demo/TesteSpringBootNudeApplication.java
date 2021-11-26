package com.example.demo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.EventListener;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreException;
import com.google.cloud.firestore.Query;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

import config.FirebaseInicializer;
import entity.Product;
import firebase.ServicosService;
import model.AcumuladoMensal;
import model.ClienteModel;
import model.ContaEntradaModel;
import model.EmpresaModel;
import model.Fornecedor;
import model.LancamentoEntradaModel;
import model.LancamentoSaidaModel;
import model.ServicoFornecedorModel;
import model.UserModel;
import service.ProductService;

@RestController
@RequestMapping("/api")
@SpringBootApplication
@EnableAutoConfiguration
@CrossOrigin("*")
public class TesteSpringBootNudeApplication {

	@Value("${app.firebase-configuration-file}")
	private String firebaseConfigPath;
	Logger logger = LoggerFactory.getLogger(FirebaseInicializer.class);

	@PostConstruct
	public void initialize() {
		try {
			FirebaseOptions options = new FirebaseOptions.Builder()
					.setCredentials(
							GoogleCredentials.fromStream(new ClassPathResource(firebaseConfigPath).getInputStream()))
					.setDatabaseUrl("https://destack360-default-rtdb.firebaseio.com").build();
			if (FirebaseApp.getApps().isEmpty()) {
				FirebaseApp.initializeApp(options);
				logger.info("Firebase application has been initialized");
			}
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}

	@GetMapping("/teste")
	public ResponseEntity<String> testGetEndpoint() {
		return ResponseEntity.ok("<h1>Teste get Endapoint is Working</h1>");
	}

	public ServicosService servicosService;
	private ProductService productService;
	private Firestore firestore;

	@GetMapping("/products")
	public List<Product> getProductsAllDetails() throws ExecutionException, InterruptedException {

		List<Product> resultado = new ArrayList<>();

		Firestore firestore = FirestoreClient.getFirestore();
		CollectionReference collectionReference = firestore.collection("produtcts");

		ApiFuture<QuerySnapshot> query = collectionReference.get();
		List<QueryDocumentSnapshot> documentSnapshots = query.get().getDocuments();
		for (QueryDocumentSnapshot doc : documentSnapshots) {
			Product productmodel = doc.toObject(Product.class);
			resultado.add(productmodel);
		}

		return resultado;
	}

	@PostMapping("/cadastrar-fornecedor")
	public String cadastrarFornecedor(@RequestBody Fornecedor fornecedor) {
		String msgCadastro = "";

		return msgCadastro;
	}

	/* Lançar Entrada Caixa */

	@PostMapping("/lancar-entrada-caixa")
	public LancamentoEntradaModel lancarEntrada(@RequestBody LancamentoEntradaModel lancamentoEntradaModel)
			throws ExecutionException, InterruptedException {

		LancamentoEntradaModel lancaMentoEntradaModelJason = new LancamentoEntradaModel();
		String mesReferencia = "";
		String identificadorCliente = lancamentoEntradaModel.getIdentificador();
		String identificadorEmpresa = lancamentoEntradaModel.getIdentificadorEmpresa();
		String dataEnviada = lancamentoEntradaModel.getDataLancamentoEntrada();
		String valorRecebido = lancamentoEntradaModel.getValorLancamentoEntrada();
		String descricaoRecebida = lancamentoEntradaModel.getDetalhesLancamentoEntrada();
		String emailEfetuandoLancamento = lancamentoEntradaModel.getEmailUserLancandoEntrada();

		// Configurando
		String[] dataRecebida = dataEnviada.split("-");
		String dataFormatadaLancamentoEntrada = dataRecebida[2] + "/" + dataRecebida[1] + "/" + dataRecebida[0];
		String anoCollection = dataRecebida[0];
		int valorMesReferenciaLancado = Integer.parseInt(dataRecebida[1]);

		switch (valorMesReferenciaLancado) {
		case 1:
			mesReferencia = "JANEIRO";
			break;
		case 2:
			mesReferencia = "FEVEREIRO";
			break;
		case 3:
			mesReferencia = "MARÇO";
			break;
		case 4:
			mesReferencia = "ABRIL";
			break;
		case 5:
			mesReferencia = "MAIO";
			break;
		case 6:
			mesReferencia = "JUNHO";
			break;
		case 7:
			mesReferencia = "JULHO";
			break;
		case 8:
			mesReferencia = "AGOSTO";
			break;
		case 9:
			mesReferencia = "SETEMBRO";
			break;
		case 10:
			mesReferencia = "OUTUBRO";
			break;
		case 11:
			mesReferencia = "NOVEMBRO";
			break;
		case 12:
			mesReferencia = "DEZEMBRO";
			break;

		default:
			break;

		}

		String nomeCollectionMesReferencia = "ACUMULADO_MES_" + mesReferencia;

		String valorLancamentoEntradaRecebido = valorRecebido;
		String valorLancamentoEntradaLimpo = valorLancamentoEntradaRecebido.replace(",", ".");
		double valorLancamentoEntradaConvertido = Double.parseDouble(valorLancamentoEntradaLimpo);

		Firestore firebaseAcumuladoClienteEmpresa = FirestoreClient.getFirestore();
		DocumentReference documentoAcumulado = firebaseAcumuladoClienteEmpresa.collection("CLIENTES-CADASTRADOS")
				.document(identificadorCliente).collection("EMPRESAS-CLIENTE").document(identificadorEmpresa)
				.collection(anoCollection).document(nomeCollectionMesReferencia);

		ApiFuture<DocumentSnapshot> acumuladoEmpresa = documentoAcumulado.get();
		DocumentSnapshot documentSnapshotUsuario = acumuladoEmpresa.get();

		if (documentSnapshotUsuario.exists()) {

			/*
			 * Se o documento existir siginigica que o usuario já efetuou lancamento de
			 * entrada
			 */

			Firestore firebaseAcumuladoRecuperado = FirestoreClient.getFirestore();
			DocumentReference documentoAcumuladoRecuperado = firebaseAcumuladoClienteEmpresa
					.collection("CLIENTES-CADASTRADOS").document(identificadorCliente).collection("EMPRESAS-CLIENTE")
					.document(identificadorEmpresa).collection(anoCollection).document(nomeCollectionMesReferencia);

			ApiFuture<DocumentSnapshot> acumuladoEmpresaRecuperado = documentoAcumulado.get();
			DocumentSnapshot documentSnapshotUsuarioRecuperado = acumuladoEmpresa.get();

			AcumuladoMensal acumuladoMensal = documentSnapshotUsuarioRecuperado.toObject(AcumuladoMensal.class);

			double valorTotalDebitoEmpresa = acumuladoMensal.getValorTotalDebitoEmpresa();
			double valotTotalCreditoEmpresa = acumuladoMensal.getValotTotalCreditoEmpresa();
			int quantidadeTotalLancamentoDebito = acumuladoMensal.getQuantidadeTotalLancamentoDebito();
			int quantidadeTotalLancamentoCredito = acumuladoMensal.getQuantidadeTotalLancamentoCredito();
			double saldoCaixa = acumuladoMensal.getSaldoCaixa();
			double saldoBanco = acumuladoMensal.getSaldoBanco();

			double valorRecebidoConvert = Double.parseDouble(valorRecebido);
			double somaSaldoCaixa = valorRecebidoConvert + saldoCaixa;

			AcumuladoMensal acumuladoAtualiza = new AcumuladoMensal();
			LancamentoEntradaModel lancamentoSalva = new LancamentoEntradaModel();

			acumuladoAtualiza.setValorTotalDebitoEmpresa(somaSaldoCaixa);
			acumuladoAtualiza.setQuantidadeTotalLancamentoCredito(quantidadeTotalLancamentoCredito);
			acumuladoAtualiza.setValotTotalCreditoEmpresa(valotTotalCreditoEmpresa);
			acumuladoAtualiza.setQuantidadeTotalLancamentoDebito(quantidadeTotalLancamentoDebito + 1);
			acumuladoAtualiza.setSaldoBanco(saldoBanco);
			acumuladoAtualiza.setSaldoCaixa(somaSaldoCaixa);

			Firestore firebaseAcumuladoatualiza = FirestoreClient.getFirestore();
			DocumentReference documentoAcumuladoAtualiza = firebaseAcumuladoClienteEmpresa
					.collection("CLIENTES-CADASTRADOS").document(identificadorCliente).collection("EMPRESAS-CLIENTE")
					.document(identificadorEmpresa).collection(anoCollection).document(nomeCollectionMesReferencia);

			documentoAcumuladoAtualiza.set(acumuladoAtualiza);

			Firestore firebaseAcumuladoAdicionaLancamento = FirestoreClient.getFirestore();
			CollectionReference documentoAcumuladoAdicionaLancamento = firebaseAcumuladoAdicionaLancamento
					.collection("CLIENTES-CADASTRADOS").document(identificadorCliente).collection("EMPRESAS-CLIENTE")
					.document(identificadorEmpresa).collection(anoCollection).document(nomeCollectionMesReferencia)
					.collection("LANCAMENTOS-ENTRADA-CAIXA");

			documentoAcumuladoAdicionaLancamento.add(lancamentoEntradaModel);

		} else {

			double valorRecebidoConvert = Double.parseDouble(valorRecebido);

			AcumuladoMensal acumuladoAtualiza = new AcumuladoMensal();
			LancamentoEntradaModel lancamentoSalva = new LancamentoEntradaModel();

			System.out.println("Passou pelo primeiro ");
			acumuladoAtualiza.setValorTotalDebitoEmpresa(valorRecebidoConvert);
			acumuladoAtualiza.setQuantidadeTotalLancamentoCredito(0);
			acumuladoAtualiza.setValotTotalCreditoEmpresa(0);
			acumuladoAtualiza.setQuantidadeTotalLancamentoDebito(1);
			acumuladoAtualiza.setSaldoBanco(0);
			acumuladoAtualiza.setSaldoCaixa(valorRecebidoConvert);
			System.out.println("Acumulado:  " + acumuladoAtualiza);

			Firestore firebaseAcumuladoatualiza = FirestoreClient.getFirestore();
			DocumentReference documentoAcumuladoAtualiza = firebaseAcumuladoClienteEmpresa
					.collection("CLIENTES-CADASTRADOS").document(identificadorCliente).collection("EMPRESAS-CLIENTE")
					.document(identificadorEmpresa).collection(anoCollection).document(nomeCollectionMesReferencia);

			documentoAcumuladoAtualiza.set(acumuladoAtualiza);

			Firestore firebaseAcumuladoEntradaCredito = FirestoreClient.getFirestore();
			CollectionReference collectionReferenciaAcumuladoEntradaCredito = firebaseAcumuladoEntradaCredito
					.collection("CLIENTES-CADASTRADOS").document(identificadorCliente).collection("EMPRESAS-CLIENTE")
					.document(identificadorEmpresa).collection(anoCollection).document(nomeCollectionMesReferencia)
					.collection("LANCAMENTOS-ENTRADA-CAIXA");

			collectionReferenciaAcumuladoEntradaCredito.add(lancamentoEntradaModel);

		}

		return lancaMentoEntradaModelJason;
	}

	/* Lançar Entrada banco */

	@PostMapping("/lancar-entrada-banco")
	public LancamentoEntradaModel lancarEntradaBanco(@RequestBody LancamentoEntradaModel lancamentoEntradaModel)
			throws ExecutionException, InterruptedException {

		LancamentoEntradaModel lancaMentoEntradaModelJason = new LancamentoEntradaModel();
		String mesReferencia = "";
		String identificadorCliente = lancamentoEntradaModel.getIdentificador();
		String identificadorEmpresa = lancamentoEntradaModel.getIdentificadorEmpresa();
		String dataEnviada = lancamentoEntradaModel.getDataLancamentoEntrada();
		String valorRecebido = lancamentoEntradaModel.getValorLancamentoEntrada();
		String descricaoRecebida = lancamentoEntradaModel.getDetalhesLancamentoEntrada();
		String emailEfetuandoLancamento = lancamentoEntradaModel.getEmailUserLancandoEntrada();

		// Configurando
		String[] dataRecebida = dataEnviada.split("-");
		String dataFormatadaLancamentoEntrada = dataRecebida[2] + "/" + dataRecebida[1] + "/" + dataRecebida[0];
		String anoCollection = dataRecebida[0];
		int valorMesReferenciaLancado = Integer.parseInt(dataRecebida[1]);

		switch (valorMesReferenciaLancado) {
		case 1:
			mesReferencia = "JANEIRO";
			break;
		case 2:
			mesReferencia = "FEVEREIRO";
			break;
		case 3:
			mesReferencia = "MARÇO";
			break;
		case 4:
			mesReferencia = "ABRIL";
			break;
		case 5:
			mesReferencia = "MAIO";
			break;
		case 6:
			mesReferencia = "JUNHO";
			break;
		case 7:
			mesReferencia = "JULHO";
			break;
		case 8:
			mesReferencia = "AGOSTO";
			break;
		case 9:
			mesReferencia = "SETEMBRO";
			break;
		case 10:
			mesReferencia = "OUTUBRO";
			break;
		case 11:
			mesReferencia = "NOVEMBRO";
			break;
		case 12:
			mesReferencia = "DEZEMBRO";
			break;

		default:
			break;

		}

		String nomeCollectionMesReferencia = "ACUMULADO_MES_" + mesReferencia;

		String valorLancamentoEntradaRecebido = valorRecebido;
		String valorLancamentoEntradaLimpo = valorLancamentoEntradaRecebido.replace(",", ".");
		double valorLancamentoEntradaConvertido = Double.parseDouble(valorLancamentoEntradaLimpo);

		Firestore firebaseAcumuladoClienteEmpresa = FirestoreClient.getFirestore();
		DocumentReference documentoAcumulado = firebaseAcumuladoClienteEmpresa.collection("CLIENTES-CADASTRADOS")
				.document(identificadorCliente).collection("EMPRESAS-CLIENTE").document(identificadorEmpresa)
				.collection(anoCollection).document(nomeCollectionMesReferencia);

		ApiFuture<DocumentSnapshot> acumuladoEmpresa = documentoAcumulado.get();
		DocumentSnapshot documentSnapshotUsuario = acumuladoEmpresa.get();

		if (documentSnapshotUsuario.exists()) {

			/*
			 * Se o documento existir siginigica que o usuario já efetuou lancamento de
			 * entrada
			 */

			Firestore firebaseAcumuladoRecuperado = FirestoreClient.getFirestore();
			DocumentReference documentoAcumuladoRecuperado = firebaseAcumuladoClienteEmpresa
					.collection("CLIENTES-CADASTRADOS").document(identificadorCliente).collection("EMPRESAS-CLIENTE")
					.document(identificadorEmpresa).collection(anoCollection).document(nomeCollectionMesReferencia);

			ApiFuture<DocumentSnapshot> acumuladoEmpresaRecuperado = documentoAcumulado.get();
			DocumentSnapshot documentSnapshotUsuarioRecuperado = acumuladoEmpresa.get();

			AcumuladoMensal acumuladoMensal = documentSnapshotUsuarioRecuperado.toObject(AcumuladoMensal.class);

			double valorTotalDebitoEmpresa = acumuladoMensal.getValorTotalDebitoEmpresa();
			double valotTotalCreditoEmpresa = acumuladoMensal.getValotTotalCreditoEmpresa();
			int quantidadeTotalLancamentoDebito = acumuladoMensal.getQuantidadeTotalLancamentoDebito();
			int quantidadeTotalLancamentoCredito = acumuladoMensal.getQuantidadeTotalLancamentoCredito();
			double saldoCaixa = acumuladoMensal.getSaldoCaixa();
			double saldoBanco = acumuladoMensal.getSaldoBanco();

			double valorRecebidoConvert = Double.parseDouble(valorRecebido);
			double somaSaldoBanco = valorRecebidoConvert + saldoBanco;
			double somarValorTotalDebitos = valorRecebidoConvert + valorTotalDebitoEmpresa;

			AcumuladoMensal acumuladoAtualiza = new AcumuladoMensal();
			LancamentoEntradaModel lancamentoSalva = new LancamentoEntradaModel();

			acumuladoAtualiza.setValorTotalDebitoEmpresa(somarValorTotalDebitos);
			acumuladoAtualiza.setQuantidadeTotalLancamentoCredito(quantidadeTotalLancamentoCredito);
			acumuladoAtualiza.setValotTotalCreditoEmpresa(valotTotalCreditoEmpresa);
			acumuladoAtualiza.setQuantidadeTotalLancamentoDebito(quantidadeTotalLancamentoDebito + 1);
			acumuladoAtualiza.setSaldoBanco(somaSaldoBanco);
			acumuladoAtualiza.setSaldoCaixa(saldoCaixa);

			Firestore firebaseAcumuladoatualiza = FirestoreClient.getFirestore();
			DocumentReference documentoAcumuladoAtualiza = firebaseAcumuladoClienteEmpresa
					.collection("CLIENTES-CADASTRADOS").document(identificadorCliente).collection("EMPRESAS-CLIENTE")
					.document(identificadorEmpresa).collection(anoCollection).document(nomeCollectionMesReferencia);

			documentoAcumuladoAtualiza.set(acumuladoAtualiza);

			Firestore firebaseAcumuladoAdicionaLancamento = FirestoreClient.getFirestore();
			CollectionReference documentoAcumuladoAdicionaLancamento = firebaseAcumuladoAdicionaLancamento
					.collection("CLIENTES-CADASTRADOS").document(identificadorCliente).collection("EMPRESAS-CLIENTE")
					.document(identificadorEmpresa).collection(anoCollection).document(nomeCollectionMesReferencia)
					.collection("LANCAMENTOS-ENTRADA-CAIXA");

			documentoAcumuladoAdicionaLancamento.add(lancamentoEntradaModel);

		} else {

			double valorRecebidoConvert = Double.parseDouble(valorRecebido);

			AcumuladoMensal acumuladoAtualiza = new AcumuladoMensal();
			LancamentoEntradaModel lancamentoSalva = new LancamentoEntradaModel();

			System.out.println("Passou pelo primeiro ");
			acumuladoAtualiza.setValorTotalDebitoEmpresa(valorRecebidoConvert);
			acumuladoAtualiza.setQuantidadeTotalLancamentoCredito(0);
			acumuladoAtualiza.setValotTotalCreditoEmpresa(0);
			acumuladoAtualiza.setQuantidadeTotalLancamentoDebito(1);
			acumuladoAtualiza.setSaldoBanco(0);
			acumuladoAtualiza.setSaldoCaixa(valorRecebidoConvert);
			System.out.println("Acumulado:  " + acumuladoAtualiza);

			Firestore firebaseAcumuladoatualiza = FirestoreClient.getFirestore();
			DocumentReference documentoAcumuladoAtualiza = firebaseAcumuladoClienteEmpresa
					.collection("CLIENTES-CADASTRADOS").document(identificadorCliente).collection("EMPRESAS-CLIENTE")
					.document(identificadorEmpresa).collection(anoCollection).document(nomeCollectionMesReferencia);

			documentoAcumuladoAtualiza.set(acumuladoAtualiza);

			Firestore firebaseAcumuladoEntradaCredito = FirestoreClient.getFirestore();
			CollectionReference collectionReferenciaAcumuladoEntradaCredito = firebaseAcumuladoEntradaCredito
					.collection("CLIENTES-CADASTRADOS").document(identificadorCliente).collection("EMPRESAS-CLIENTE")
					.document(identificadorEmpresa).collection(anoCollection).document(nomeCollectionMesReferencia)
					.collection("LANCAMENTOS-ENTRADA-CAIXA");

			collectionReferenciaAcumuladoEntradaCredito.add(lancamentoEntradaModel);

		}

		return lancaMentoEntradaModelJason;
	}

	
	/* Lançar Saida Caixa */
	 
	  @PostMapping("/lancar-saida-caixa") public LancamentoSaidaModel
	 lancarSaidaCaixa(@RequestBody LancamentoSaidaModel lancamentoSaida) throws
	  ExecutionException, InterruptedException {
	
		  LancamentoSaidaModel lancaMentoEntradaModelJason = new	LancamentoSaidaModel(); 
	  
	  String mesReferencia = ""; String
	  identificadorCliente = lancamentoSaida.getIdentificador(); String
	  identificadorEmpresa = lancamentoSaida.getIdentificadorEmpresa(); String
	  dataEnviada = lancamentoSaida.getDataLancamentoSaida() ; 
	  String valorRecebido = lancamentoSaida.getValorLancamentoSaida(); 
	  String fornecedorRecebido =
	  lancamentoSaida.getFornecedor(); 
	  String servicoLancamentoSaida =
	  lancamentoSaida.getServico(); 
	  String emailEfetuandoLancamento =
	  lancamentoSaida.getEmailUserLancandoSaida();
	  
	  
	  
	  
	  //Configurando 
	  String [] dataRecebida = dataEnviada.split("-"); 
	  String dataFormatadaLancamentoEntrada = dataRecebida[2]+ "/" +dataRecebida[1]+ "/"
	  +dataRecebida[0]; String anoCollection = dataRecebida[0]; int
	  valorMesReferenciaLancado = Integer.parseInt(dataRecebida[1]);
	  
	  switch (valorMesReferenciaLancado){ case 1: mesReferencia = "JANEIRO"; break;
	  case 2: mesReferencia = "FEVEREIRO"; break; case 3: mesReferencia = "MARÇO";
	  break; case 4: mesReferencia = "ABRIL"; break; case 5: mesReferencia =
	  "MAIO"; break; case 6: mesReferencia = "JUNHO"; break; case 7: mesReferencia
	  = "JULHO"; break; case 8: mesReferencia = "AGOSTO"; break; case 9:
	  mesReferencia = "SETEMBRO"; break; case 10: mesReferencia = "OUTUBRO"; break;
	  case 11: mesReferencia = "NOVEMBRO"; break; case 12: mesReferencia =
	  "DEZEMBRO"; break;
	  
	  default: break;
	  
	  }
	  
	  
	  
	  
	  
	  
	  String nomeCollectionMesReferencia = "ACUMULADO_MES_"+ mesReferencia;
	  
	  String valorLancamentoEntradaRecebido = valorRecebido; String
	  valorLancamentoEntradaLimpo = valorLancamentoEntradaRecebido.replace(",",
	  "."); double valorLancamentoEntradaConvertido =
	  Double.parseDouble(valorLancamentoEntradaLimpo);
	  
	  
	  Firestore firebaseAcumuladoClienteEmpresa = FirestoreClient.getFirestore();
	  DocumentReference documentoAcumulado = firebaseAcumuladoClienteEmpresa
	  .collection("CLIENTES-CADASTRADOS") .document(identificadorCliente)
	  .collection("EMPRESAS-CLIENTE") .document(identificadorEmpresa)
	  .collection(anoCollection) .document(nomeCollectionMesReferencia);
	  
	  ApiFuture<DocumentSnapshot> acumuladoEmpresa = documentoAcumulado.get();
	  DocumentSnapshot documentSnapshotUsuario = acumuladoEmpresa.get();
	  
	  if(documentSnapshotUsuario.exists()) {
	  
	 /* 
	  Se o documento existir siginigica que o usuario já efetuou lancamento de
	  entrada
	  */
	  Firestore firebaseAcumuladoRecuperado = FirestoreClient.getFirestore();
	  DocumentReference documentoAcumuladoRecuperado =
	  firebaseAcumuladoClienteEmpresa .collection("CLIENTES-CADASTRADOS")
	  .document(identificadorCliente) .collection("EMPRESAS-CLIENTE")
	  .document(identificadorEmpresa) .collection(anoCollection)
	  .document(nomeCollectionMesReferencia);
	  
	  ApiFuture<DocumentSnapshot> acumuladoEmpresaRecuperado =
	  documentoAcumulado.get(); DocumentSnapshot documentSnapshotUsuarioRecuperado
	  = acumuladoEmpresa.get();
	  
	  
	  AcumuladoMensal acumuladoMensal =
	  documentSnapshotUsuarioRecuperado.toObject(AcumuladoMensal.class);
	  
	  double valorTotalDebitoEmpresa = acumuladoMensal.getValorTotalDebitoEmpresa()
	  ; double valotTotalCreditoEmpresa =
	  acumuladoMensal.getValotTotalCreditoEmpresa(); int
	  quantidadeTotalLancamentoDebito =
	  acumuladoMensal.getQuantidadeTotalLancamentoDebito(); int
	  quantidadeTotalLancamentoCredito =
	  acumuladoMensal.getQuantidadeTotalLancamentoCredito(); double saldoCaixa =
	  acumuladoMensal.getSaldoCaixa(); double saldoBanco =
	  acumuladoMensal.getSaldoBanco();
	  
	  
	  double valorRecebidoConvert = Double.parseDouble(valorRecebido); 
	  double subtrairSaldoCaixa = saldoCaixa - valorRecebidoConvert;
	  double valorTotalCreditoCaixa = valorRecebidoConvert + valotTotalCreditoEmpresa;
	  
	  AcumuladoMensal acumuladoAtualiza = new AcumuladoMensal();
	  LancamentoEntradaModel lancamentoSalva = new LancamentoEntradaModel();
	  
	  
	  acumuladoAtualiza.setValorTotalDebitoEmpresa(saldoCaixa);
	  acumuladoAtualiza.setQuantidadeTotalLancamentoCredito(
	  quantidadeTotalLancamentoCredito + 1);
	  acumuladoAtualiza.setValotTotalCreditoEmpresa(valorTotalCreditoCaixa);
	  acumuladoAtualiza.setQuantidadeTotalLancamentoDebito(
	  quantidadeTotalLancamentoDebito);
	  acumuladoAtualiza.setSaldoBanco(saldoBanco);
	  acumuladoAtualiza.setSaldoCaixa(subtrairSaldoCaixa);
	  
	  Firestore firebaseAcumuladoatualiza = FirestoreClient.getFirestore();
	  DocumentReference documentoAcumuladoAtualiza =
	  firebaseAcumuladoClienteEmpresa .collection("CLIENTES-CADASTRADOS")
	  .document(identificadorCliente) .collection("EMPRESAS-CLIENTE")
	  .document(identificadorEmpresa) .collection(anoCollection)
	  .document(nomeCollectionMesReferencia);
	  
	  
	  documentoAcumuladoAtualiza.set(acumuladoAtualiza);
	  
	  
	  Firestore firebaseAcumuladoAdicionaLancamento =
	  FirestoreClient.getFirestore(); CollectionReference
	  documentoAcumuladoAdicionaLancamento = firebaseAcumuladoAdicionaLancamento
	  .collection("CLIENTES-CADASTRADOS") .document(identificadorCliente)
	  .collection("EMPRESAS-CLIENTE") .document(identificadorEmpresa)
	  .collection(anoCollection) .document(nomeCollectionMesReferencia)
	  .collection("LANCAMENTOS-SAIDA-CAIXA");
	  
	  documentoAcumuladoAdicionaLancamento.add(lancamentoSaida);
	  
	  
	  }else {
	  
	  
	  double valorRecebidoConvert = Double.parseDouble(valorRecebido);
	  
	  
	  
	  AcumuladoMensal acumuladoAtualiza = new AcumuladoMensal();
	  LancamentoEntradaModel lancamentoSalva = new LancamentoEntradaModel();
	  
	  System.out.println("Passou pelo primeiro ");
	  acumuladoAtualiza.setValorTotalDebitoEmpresa(valorRecebidoConvert);
	  acumuladoAtualiza.setQuantidadeTotalLancamentoCredito(1);
	  acumuladoAtualiza.setValotTotalCreditoEmpresa(valorRecebidoConvert);
	  acumuladoAtualiza.setQuantidadeTotalLancamentoDebito(0);	  
	  
	  acumuladoAtualiza.setSaldoBanco(0);
	  acumuladoAtualiza.setSaldoCaixa(0 - valorRecebidoConvert);
	  System.out.println("Acumulado:  " + acumuladoAtualiza);
	  
	  
	  Firestore firebaseAcumuladoatualiza = FirestoreClient.getFirestore();
	  DocumentReference documentoAcumuladoAtualiza =
	  firebaseAcumuladoClienteEmpresa .collection("CLIENTES-CADASTRADOS")
	  .document(identificadorCliente) .collection("EMPRESAS-CLIENTE")
	  .document(identificadorEmpresa) .collection(anoCollection)
	  .document(nomeCollectionMesReferencia);
	  
	  
	  documentoAcumuladoAtualiza.set(acumuladoAtualiza);
	  
	  
	  Firestore firebaseAcumuladoEntradaCredito = FirestoreClient.getFirestore();
	  CollectionReference collectionReferenciaAcumuladoEntradaCredito =
	  firebaseAcumuladoEntradaCredito.collection("CLIENTES-CADASTRADOS")
	  .document(identificadorCliente) .collection("EMPRESAS-CLIENTE")
	  .document(identificadorEmpresa) .collection(anoCollection)
	  .document(nomeCollectionMesReferencia)
	  .collection("LANCAMENTOS-SAIDA-CAIXA");
	  
	  collectionReferenciaAcumuladoEntradaCredito.add(lancamentoSaida);
	  
	  
	  }
	  
	  
	  
	  
	  return lancaMentoEntradaModelJason; }
	  
	  //Lançar Saida banco
	 
	  @PostMapping("/lancar-saida-banco") public LancamentoSaidaModel
	  lancarSaidaBanco(@RequestBody LancamentoSaidaModel lancamentoSaida)
	  throws ExecutionException, InterruptedException {
	  
		  LancamentoSaidaModel lancaMentoEntradaModelJason = new	LancamentoSaidaModel(); 
		  
		  String mesReferencia = ""; String
		  identificadorCliente = lancamentoSaida.getIdentificador(); String
		  identificadorEmpresa = lancamentoSaida.getIdentificadorEmpresa(); String
		  dataEnviada = lancamentoSaida.getDataLancamentoSaida() ; 
		  String valorRecebido = lancamentoSaida.getValorLancamentoSaida(); 
		  String fornecedorRecebido =
		  lancamentoSaida.getFornecedor(); 
		  String servicoLancamentoSaida =
		  lancamentoSaida.getServico(); 
		  String emailEfetuandoLancamento =
		  lancamentoSaida.getEmailUserLancandoSaida();
	  
	  
	  
	  //Configurando 
	  String [] dataRecebida = dataEnviada.split("-"); String
	  dataFormatadaLancamentoEntrada = dataRecebida[2]+ "/" +dataRecebida[1]+ "/"
	  +dataRecebida[0]; String anoCollection = dataRecebida[0]; int
	  valorMesReferenciaLancado = Integer.parseInt(dataRecebida[1]);
	  
	  switch (valorMesReferenciaLancado){ case 1: mesReferencia = "JANEIRO"; break;
	  case 2: mesReferencia = "FEVEREIRO"; break; case 3: mesReferencia = "MARÇO";
	  break; case 4: mesReferencia = "ABRIL"; break; case 5: mesReferencia =
	  "MAIO"; break; case 6: mesReferencia = "JUNHO"; break; case 7: mesReferencia
	  = "JULHO"; break; case 8: mesReferencia = "AGOSTO"; break; case 9:
	  mesReferencia = "SETEMBRO"; break; case 10: mesReferencia = "OUTUBRO"; break;
	  case 11: mesReferencia = "NOVEMBRO"; break; case 12: mesReferencia =
	  "DEZEMBRO"; break;
	  
	  default: break;
	  
	  }
	  
	  
	  String nomeCollectionMesReferencia = "ACUMULADO_MES_"+ mesReferencia;
	  
	  String valorLancamentoEntradaRecebido = valorRecebido; String
	  valorLancamentoEntradaLimpo = valorLancamentoEntradaRecebido.replace(",",
	  "."); double valorLancamentoEntradaConvertido =
	  Double.parseDouble(valorLancamentoEntradaLimpo);
	  
	  
	  Firestore firebaseAcumuladoClienteEmpresa = FirestoreClient.getFirestore();
	  DocumentReference documentoAcumulado = firebaseAcumuladoClienteEmpresa
	  .collection("CLIENTES-CADASTRADOS") .document(identificadorCliente)
	  .collection("EMPRESAS-CLIENTE") .document(identificadorEmpresa)
	  .collection(anoCollection) .document(nomeCollectionMesReferencia) ;
	  
	  ApiFuture<DocumentSnapshot> acumuladoEmpresa = documentoAcumulado.get();
	  DocumentSnapshot documentSnapshotUsuario = acumuladoEmpresa. get();
	  
	  if(documentSnapshotUsuario.exists()) {
	  
	
			/*
			 * Se o documento existir siginigica que o usuario já efetuou lancamento de
			 * entrada ou saida
			 */
	  
	  Firestore firebaseAcumuladoRecuperado = FirestoreClient.getFirestore();
	  DocumentReference documentoAcumuladoRecuperado =
	 firebaseAcumuladoClienteEmpresa .collection("CLIENTES-CADASTRADOS")
	  .document(identificadorCliente) .collection("EMPRESAS-CLIENTE")
	  .document(identificadorEmpresa) .collection(anoCollection)
	  .document(nomeCollectionMesReferencia);
	  
	  ApiFuture<DocumentSnapshot> acumuladoEmpresaRecuperado =
	  documentoAcumulado.get(); DocumentSnapshot documentSnapshotUsuarioRecuperado
	 = acumuladoEmpresa.get();
	 
	 
	  AcumuladoMensal acumuladoMensal =
	  documentSnapshotUsuarioRecuperado.toObject(AcumuladoMensal.class);
	  
	  double valorTotalDebitoEmpresa = acumuladoMensal.getValorTotalDebitoEmpresa()
	  ; double valotTotalCreditoEmpresa =
	  acumuladoMensal.getValotTotalCreditoEmpresa(); int
	  quantidadeTotalLancamentoDebito =
	  acumuladoMensal.getQuantidadeTotalLancamentoDebito(); int
	  quantidadeTotalLancamentoCredito =
	  acumuladoMensal.getQuantidadeTotalLancamentoCredito(); double saldoCaixa =
	  acumuladoMensal.getSaldoCaixa(); double saldoBanco =
	  acumuladoMensal.getSaldoBanco();
	  

	  double valorRecebidoConvert = Double.parseDouble(valorRecebido); 
	  double subtrairSaldoCaixa = saldoBanco - valorRecebidoConvert;
	  double valorTotalCreditoCaixa = valorRecebidoConvert + valotTotalCreditoEmpresa;
	  
	  AcumuladoMensal acumuladoAtualiza = new AcumuladoMensal();
	  LancamentoEntradaModel lancamentoSalva = new LancamentoEntradaModel();
	  
	  
	  acumuladoAtualiza.setValorTotalDebitoEmpresa(saldoCaixa);
	  acumuladoAtualiza.setQuantidadeTotalLancamentoCredito(
	  quantidadeTotalLancamentoCredito + 1);
	  acumuladoAtualiza.setValotTotalCreditoEmpresa(valorTotalCreditoCaixa);
	  acumuladoAtualiza.setQuantidadeTotalLancamentoDebito(
	  quantidadeTotalLancamentoDebito);
	  acumuladoAtualiza.setSaldoBanco(subtrairSaldoCaixa);
	  acumuladoAtualiza.setSaldoCaixa(saldoCaixa);
	  
	  Firestore firebaseAcumuladoatualiza = FirestoreClient.getFirestore();
	  DocumentReference documentoAcumuladoAtualiza =
	  firebaseAcumuladoClienteEmpresa .collection("CLIENTES-CADASTRADOS")
	  .document(identificadorCliente) .collection("EMPRESAS-CLIENTE")
	  .document(identificadorEmpresa) .collection(anoCollection)
	  .document(nomeCollectionMesReferencia);
	  
	  
	  documentoAcumuladoAtualiza.set(acumuladoAtualiza);
	  
	  
	  Firestore firebaseAcumuladoAdicionaLancamento =
	  FirestoreClient.getFirestore(); CollectionReference
	  documentoAcumuladoAdicionaLancamento = firebaseAcumuladoAdicionaLancamento
	  .collection("CLIENTES-CADASTRADOS") .document(identificadorCliente)
	  .collection("EMPRESAS-CLIENTE") .document(identificadorEmpresa)
	  .collection(anoCollection) .document(nomeCollectionMesReferencia)
	  .collection("LANCAMENTOS-SAIDA-CAIXA");
	  
	  documentoAcumuladoAdicionaLancamento.add(lancamentoSaida);
	  
	  
	  }else {
	  
	  
		  double valorRecebidoConvert = Double.parseDouble(valorRecebido);
		  
		  
		  
		  AcumuladoMensal acumuladoAtualiza = new AcumuladoMensal();
		  LancamentoEntradaModel lancamentoSalva = new LancamentoEntradaModel();
		  
		  System.out.println("Passou pelo primeiro ");
		  acumuladoAtualiza.setValorTotalDebitoEmpresa(valorRecebidoConvert);
		  acumuladoAtualiza.setQuantidadeTotalLancamentoCredito(1);
		  acumuladoAtualiza.setValotTotalCreditoEmpresa(valorRecebidoConvert);
		  acumuladoAtualiza.setQuantidadeTotalLancamentoDebito(0);	  
		  
		  acumuladoAtualiza.setSaldoBanco(0);
		  acumuladoAtualiza.setSaldoCaixa(0 - valorRecebidoConvert);
		  System.out.println("Acumulado:  " + acumuladoAtualiza);
		  
		  
		  Firestore firebaseAcumuladoatualiza = FirestoreClient.getFirestore();
		  DocumentReference documentoAcumuladoAtualiza =
		  firebaseAcumuladoClienteEmpresa .collection("CLIENTES-CADASTRADOS")
		  .document(identificadorCliente) .collection("EMPRESAS-CLIENTE")
		  .document(identificadorEmpresa) .collection(anoCollection)
		  .document(nomeCollectionMesReferencia);
	  
	  
	  documentoAcumuladoAtualiza.set(acumuladoAtualiza);
	  
	  
	  Firestore firebaseAcumuladoEntradaCredito = FirestoreClient.getFirestore();
	  CollectionReference collectionReferenciaAcumuladoEntradaCredito =
	  firebaseAcumuladoEntradaCredito.collection("CLIENTES-CADASTRADOS")
	  .document(identificadorCliente) .collection("EMPRESAS-CLIENTE")
	  .document(identificadorEmpresa) .collection(anoCollection)
	  .document(nomeCollectionMesReferencia)
	  .collection("LANCAMENTOS-SAIDA-CAIXA");
	  
	  collectionReferenciaAcumuladoEntradaCredito.add(lancamentoSaida);
	  
 
	  }
	  
	  
	  
	  
	  return lancaMentoEntradaModelJason; }
	  
	 

	/* Testando outro methodo */
	/*
	 * @GetMapping("/testeCalculo") public double getCalculo() {
	 * 
	 * 
	 * return testeCulculo(); }
	 */
	// Cadastrar Conta de Entrada
	@PostMapping("/cadastrarContaEntrada")
	public ContaEntradaModel contaEntradaModel(@RequestBody ContaEntradaModel contaEntradaModel)
			throws ExecutionException, InterruptedException {
		ContaEntradaModel contaEntradaModelJason = new ContaEntradaModel();
		String mensagemReturn = "";
		String codigoCEntrada = contaEntradaModel.getCodigoC();
		String codigoDEntrada = contaEntradaModel.getCodigoD();
		String descricao = contaEntradaModel.getDescricao();
		if (codigoCEntrada != null && codigoDEntrada != null && descricao != null) {
			Firestore firestoreContaEntra = FirestoreClient.getFirestore();
			DocumentReference documentReferenceCliente;
			documentReferenceCliente = firestoreContaEntra.collection("CONTAS_ENTRADA").document(codigoCEntrada);

			ApiFuture<DocumentSnapshot> documentSnapshotApiFutureCliente = documentReferenceCliente.get();
			DocumentSnapshot documentSnapshotCliente = documentSnapshotApiFutureCliente.get();

			if (documentSnapshotCliente.exists()) {
				mensagemReturn = "Cliente já tem cadastro";
				contaEntradaModelJason = null;
			} else {

				contaEntradaModelJason = new ContaEntradaModel();
				contaEntradaModelJason.setCodigoC(codigoCEntrada);
				contaEntradaModelJason.setCodigoD(codigoDEntrada);
				contaEntradaModelJason.setDescricao(descricao);
				contaEntradaModelJason.setIdendificador(codigoCEntrada);
				documentReferenceCliente.set(contaEntradaModelJason);

			}

		} else {
			mensagemReturn = "CNPJ DEU NULO";
		}

		/*
		 * this.mensagemReturn = "Razao social: " + this.razaoSocial +"CNPJ: " +
		 * this.CNPJ +"Usuario: " + this.Usuario + "email : " + this.emailCliente
		 * +" telefone: " + this.telefone +" celular: " + this.celular +" OBS: " +
		 * this.OBS;
		 */
		return contaEntradaModelJason;

	}

	/* Recuperar Clientes */
	@GetMapping("/recuperarClientes")
	public List<ClienteModel> listaClientesCadastrados() throws ExecutionException, InterruptedException {
		List<ClienteModel> resultadoClientesCadastrados = new ArrayList<>();

		Firestore firestoreClientesCadastrados = FirestoreClient.getFirestore();
		CollectionReference collectionReferenceClientesCadastrados = firestoreClientesCadastrados
				.collection("CLIENTES");

		ApiFuture<QuerySnapshot> query = collectionReferenceClientesCadastrados.get();
		List<QueryDocumentSnapshot> documentSnapshots = query.get().getDocuments();
		for (QueryDocumentSnapshot doc : documentSnapshots) {
			ClienteModel clienteCadastrado = doc.toObject(ClienteModel.class);
			resultadoClientesCadastrados.add(clienteCadastrado);
		}
		if (resultadoClientesCadastrados.size() == 0) {
			return null;
		} else {
			return resultadoClientesCadastrados;
		}

	}
	
	/*Recuperar servico forncedor selecionado*/
	
	@PostMapping("/recuperar-servicos-fornecedor-selecionado")
	public List<ServicoFornecedorModel> recuperarServicosFornecedor(@RequestBody String idPassado)
			throws ExecutionException, InterruptedException {
		List<ServicoFornecedorModel> servicosRecuperados = new ArrayList<>();
		
		String idrecuperado = idPassado;
		ServicoFornecedorModel servicoFornecedor = new ServicoFornecedorModel();

		if (idrecuperado != null) {

			Firestore firestore = FirestoreClient.getFirestore();
			CollectionReference documentReferenceUsuario = 
					firestore.collection("FORNECEDORES").document(idrecuperado)
					.collection("SERVIÇOS");

			ApiFuture<QuerySnapshot> query = documentReferenceUsuario.get();
			List<QueryDocumentSnapshot> documentSnapshots = query.get().getDocuments();
			for (QueryDocumentSnapshot doc : documentSnapshots) {
				ServicoFornecedorModel contaEntradaCadastradaBanco = doc.toObject(ServicoFornecedorModel.class);
				servicosRecuperados.add(contaEntradaCadastradaBanco);
			}
		}

		return servicosRecuperados;
	}

	/* Recuperar Contas Entrada -> todas */

	@GetMapping("/recuperarContasEntrada")
	public List<ContaEntradaModel> listaContasEntradaCadastradas() throws ExecutionException, InterruptedException {
		List<ContaEntradaModel> resultadoListaContasEntradaCadastradas = new ArrayList<>();

		Firestore firestore = FirestoreClient.getFirestore();
		CollectionReference collectionReference = firestore.collection("CONTAS_ENTRADA");

		ApiFuture<QuerySnapshot> query = collectionReference.get();
		List<QueryDocumentSnapshot> documentSnapshots = query.get().getDocuments();
		for (QueryDocumentSnapshot doc : documentSnapshots) {
			ContaEntradaModel contaEntradaCadastradaBanco = doc.toObject(ContaEntradaModel.class);
			resultadoListaContasEntradaCadastradas.add(contaEntradaCadastradaBanco);
		}
		return resultadoListaContasEntradaCadastradas;
	}

	/* Recuperar Fornecedores -> todas */

	@GetMapping("/recuperar-fornecedores-cadastrados")
	public List<Fornecedor> listaFornecedoresCadastrados() throws ExecutionException, InterruptedException {
		List<Fornecedor> resultadoListaFornecedoresCadastrados = new ArrayList<>();

		Firestore firestore = FirestoreClient.getFirestore();
		CollectionReference collectionReference = firestore.collection("FORNECEDORES");

		ApiFuture<QuerySnapshot> query = collectionReference.get();
		List<QueryDocumentSnapshot> documentSnapshots = query.get().getDocuments();
		for (QueryDocumentSnapshot doc : documentSnapshots) {
			Fornecedor contaEntradaCadastradaBanco = doc.toObject(Fornecedor.class);
			resultadoListaFornecedoresCadastrados.add(contaEntradaCadastradaBanco);
		}
		return resultadoListaFornecedoresCadastrados;
	}

	@PostMapping("/recuperarInformacoesContaEntrada")
	public ContaEntradaModel recuperarContaEntrada(@RequestBody String idPassado)
			throws ExecutionException, InterruptedException {

		String idrecuperado = idPassado;
		ContaEntradaModel contaEntradaModelInfo = new ContaEntradaModel();

		if (idrecuperado != null) {

			Firestore firestore = FirestoreClient.getFirestore();
			DocumentReference documentReferenceUsuario = firestore.collection("CONTAS_ENTRADA").document(idrecuperado);

			ApiFuture<DocumentSnapshot> documentSnapshotApiFutureInformacoesUsuarioModel = documentReferenceUsuario
					.get();
			DocumentSnapshot documentSnapshotInformacoesUserModel = documentSnapshotApiFutureInformacoesUsuarioModel
					.get();

			if (documentSnapshotInformacoesUserModel.exists()) {

				ContaEntradaModel contaEntradaRecuperada = documentSnapshotInformacoesUserModel
						.toObject(ContaEntradaModel.class);
				contaEntradaModelInfo.setIdendificador(contaEntradaRecuperada.getIdendificador());
				contaEntradaModelInfo.setCodigoC(contaEntradaRecuperada.getCodigoC());
				contaEntradaModelInfo.setCodigoD(contaEntradaRecuperada.getCodigoD());
				contaEntradaModelInfo.setDescricao(contaEntradaRecuperada.getDescricao());
			}

		}

		return contaEntradaModelInfo;
	}

	/* Lista Fornecedores */
	@GetMapping("/listafornecedores")
	public List<Fornecedor> getFornecedores() throws ExecutionException, InterruptedException {

		List<Fornecedor> resultado = new ArrayList<>();

		Firestore firestore = FirestoreClient.getFirestore();
		CollectionReference collectionReference = firestore.collection("FORNECEDORES");

		ApiFuture<QuerySnapshot> query = collectionReference.get();
		List<QueryDocumentSnapshot> documentSnapshots = query.get().getDocuments();
		for (QueryDocumentSnapshot doc : documentSnapshots) {
			Fornecedor fornecedoresCadastrados = doc.toObject(Fornecedor.class);
			resultado.add(fornecedoresCadastrados);
		}
		return resultado;
	}

	/* Lista Clientes */
	@GetMapping("/listaClientes")
	public List<ClienteModel> getClientes() throws ExecutionException, InterruptedException {

		List<ClienteModel> resultado = new ArrayList<>();

		Firestore firestore = FirestoreClient.getFirestore();
		CollectionReference collectionReference = firestore.collection("CLIENTES-CADASTRADOS");

		ApiFuture<QuerySnapshot> query = collectionReference.get();
		List<QueryDocumentSnapshot> documentSnapshots = query.get().getDocuments();
		for (QueryDocumentSnapshot doc : documentSnapshots) {
			ClienteModel fornecedoresCadastrados = doc.toObject(ClienteModel.class);
			resultado.add(fornecedoresCadastrados);
		}
		return resultado;
	}

	/*
	 * Recuperar informações do cliente id passado pelo front-end para recuperar as
	 * informações
	 */
	@PostMapping("/recuperarInformacoesCliente")
	public ClienteModel informacoesUsuario(@RequestBody String idPassado)
			throws ExecutionException, InterruptedException {

		String idrecuperado = idPassado;
		ClienteModel clienteRecuperado = new ClienteModel();
		if (idPassado != null) {

			Firestore firestore = FirestoreClient.getFirestore();
			DocumentReference documentReferenceUsuario = firestore.collection("CLIENTES-CADASTRADOS")
					.document(idrecuperado);

			ApiFuture<DocumentSnapshot> documentSnapshotApiFutureInformacoesUsuarioModel = documentReferenceUsuario
					.get();
			DocumentSnapshot documentSnapshotInformacoesUserModel = documentSnapshotApiFutureInformacoesUsuarioModel
					.get();

			if (documentSnapshotInformacoesUserModel.exists()) {

				ClienteModel usuarioRecuperadoFirebase = documentSnapshotInformacoesUserModel
						.toObject(ClienteModel.class);
				clienteRecuperado.setIdentificador(usuarioRecuperadoFirebase.getIdentificador());
				clienteRecuperado.setUsuariocliente(usuarioRecuperadoFirebase.getUsuariocliente());
				clienteRecuperado.setEmailCliente(usuarioRecuperadoFirebase.getEmailCliente());
				clienteRecuperado.setCpf(usuarioRecuperadoFirebase.getCPF());
				clienteRecuperado
						.setQuantidadeEmpresaCadastradas(usuarioRecuperadoFirebase.getQuantidadeEmpresaCadastradas());
				clienteRecuperado.setObs(usuarioRecuperadoFirebase.getObs());
				clienteRecuperado.setCelular(usuarioRecuperadoFirebase.getCelular());
				clienteRecuperado.setTelefone(usuarioRecuperadoFirebase.getTelefone());
				clienteRecuperado.setNome(usuarioRecuperadoFirebase.getNome());

			}

		}

		return clienteRecuperado;
	}

	@PostMapping("/cadastrar-empresa-cliente")
	public EmpresaModel cadastrarEmpresaCliente(@RequestBody EmpresaModel empresaCadastro)
			throws ExecutionException, InterruptedException {
		String msgRetorno = "";

		System.out.println("Entrou no cadastrar Empresa");
		ClienteModel clienteAtualizaQuantEmpresa = new ClienteModel();

		Firestore dbEmpresaCadastradasCliente = FirestoreClient.getFirestore();
		DocumentReference documentReferenceCliente;
		documentReferenceCliente = dbEmpresaCadastradasCliente.collection("CLIENTES-CADASTRADOS")
				.document(empresaCadastro.getIdentificadorCliente());

		ApiFuture<DocumentSnapshot> clienteCadastraEmprsa = documentReferenceCliente.get();
		DocumentSnapshot documentClienteReferencia = clienteCadastraEmprsa.get();

		ClienteModel clienteRecuperadoBD = documentClienteReferencia.toObject(ClienteModel.class);

		clienteAtualizaQuantEmpresa.setEmailCliente(clienteRecuperadoBD.getEmailCliente());
		clienteAtualizaQuantEmpresa.setCpf(clienteRecuperadoBD.getCPF());
		clienteAtualizaQuantEmpresa.setUsuariocliente(clienteRecuperadoBD.getUsuariocliente());
		clienteAtualizaQuantEmpresa.setCelular(clienteRecuperadoBD.getCelular());
		clienteAtualizaQuantEmpresa.setTelefone(clienteRecuperadoBD.getTelefone());
		clienteAtualizaQuantEmpresa.setNome(clienteRecuperadoBD.getNome());
		clienteAtualizaQuantEmpresa.setObs(clienteRecuperadoBD.getObs());
		clienteAtualizaQuantEmpresa.setObs(clienteRecuperadoBD.getObs());
		clienteAtualizaQuantEmpresa.setObs(clienteRecuperadoBD.getObs());
		clienteAtualizaQuantEmpresa.setIdentificador(clienteRecuperadoBD.getIdentificador());
		clienteAtualizaQuantEmpresa
				.setQuantidadeEmpresaCadastradas(clienteRecuperadoBD.getQuantidadeEmpresaCadastradas() + 1);
		clienteAtualizaQuantEmpresa.setStatus(clienteRecuperadoBD.getStatus());		
		
		
		Firestore dbCliente = FirestoreClient.getFirestore();

		dbCliente.collection("CLIENTES-CADASTRADOS").document(clienteAtualizaQuantEmpresa.getCPF())
				.set(clienteAtualizaQuantEmpresa);
		
		Firestore dbEmpresaCliente = FirestoreClient.getFirestore();
		dbEmpresaCliente.collection("CLIENTES-CADASTRADOS").document(empresaCadastro.getIdentificadorCliente())
				.collection("EMPRESAS-CLIENTE").document(empresaCadastro.getCnpj()).set(empresaCadastro);

		return empresaCadastro;
	}

	@PostMapping("/recuperar-empresa-cliente")
	public List<EmpresaModel> recuperarEmpresaCliente(@RequestBody String identificadorCliente)
			throws ExecutionException, InterruptedException {
		List<EmpresaModel> resultadoEmpresasCadastradas = new ArrayList<>();

		Firestore empresasCadastradasCliente = FirestoreClient.getFirestore();
		CollectionReference collectionReferenceClientesCadastrados = empresasCadastradasCliente
				.collection("CLIENTES-CADASTRADOS").document(identificadorCliente).collection("EMPRESAS-CLIENTE");

		ApiFuture<QuerySnapshot> query = collectionReferenceClientesCadastrados.get();
		List<QueryDocumentSnapshot> documentSnapshots = query.get().getDocuments();
		for (QueryDocumentSnapshot doc : documentSnapshots) {
			EmpresaModel empresaCadastrada = doc.toObject(EmpresaModel.class);
			resultadoEmpresasCadastradas.add(empresaCadastrada);
		}
		if (resultadoEmpresasCadastradas.size() == 0) {
			return null;
		} else {
			return resultadoEmpresasCadastradas;
		}

	}

	public void adicionarEmpresaClienteAtualizaQuantidadeEmpresa(ClienteModel clienteAtualizaQuantEmpresa,
			EmpresaModel empresaCadastro) {

		Firestore dbCliente = FirestoreClient.getFirestore();

		dbCliente.collection("CLIENTES-CADASTRADOS").document(clienteAtualizaQuantEmpresa.getCPF())
				.set(clienteAtualizaQuantEmpresa);
		System.out.println("Entrou no adicioarAtuaizarQuantidade");
		System.out.println("O que está pegando cliente: " + clienteAtualizaQuantEmpresa);
		System.out.println("O que está pegando empresa: " + empresaCadastro);

		adicionarEmpresaCliente(empresaCadastro);

	}

	public void adicionarEmpresaCliente(EmpresaModel empresaCadastro) {
		// TODO Auto-generated method stub
		Firestore dbEmpresaCliente = FirestoreClient.getFirestore();
		dbEmpresaCliente.collection("CLIENTES-CADASTRADOS").document(empresaCadastro.getIdentificadorCliente())
				.collection("EMPRESAS-CLIENTE").document(empresaCadastro.getCnpj()).set(empresaCadastro);
		System.out.println("O que está pegando: " + empresaCadastro);
		System.out.println("Entrou no adicionar empresa cliente");
	}

	public static void main(String[] args) throws Exception {

		SpringApplication.run(TesteSpringBootNudeApplication.class, args);

	}

}
