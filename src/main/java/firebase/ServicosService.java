package firebase;

import model.*;
import model.ClienteModel;
import model.ContaEntradaLancamentosFuturosModel;
import model.ContaEntradaModel;
import model.ContaSaidaModel;
import model.LancamentoEntradaModel;
import model.LancamentoSaidaModel;
import model.UserModel;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.*;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Service;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ExecutionException;

@Service
public class ServicosService {

    //Firebase
    private static final String NOME_COLLECTION_USUARIO_LANCA = "users";
    private static final String NOME_COLLECTION_ACUMULADOS = "ACUMULADOS";
    private String NOME_COLLECTION_ACUMULADOS_ENTRADA_USUARIO = "ACUMULADOS_";
    private static final String NOME_COLLECTION_LANCAMENTO_ENTRADA_DIARIA_USUARIO = "ACUMULADOS_ENTRADA_DIARIA";
    private static final String NOME_COLLECTION_LANCAMENTO_SAIDA_DIARIA_USUARIO = "ACUMULADOS_SAIDA_DIARIA";
    private static final String NOME_COLLECTION_CLIENTE = "CLIENTES";
    private static final String NOME_COLLECTION_CONTAS = "CONTAS";
    private static final String NOME_COLLECTION_CONTAS_CONTAS_ENTRADA = "CONTAS_ENTRADA";
    private static final String NOME_COLLECTION_CONTAS_CONTAS_SAIDA = "CONTAS_SAIDA";


    //Classes
    public ClienteModel clienteJason = new ClienteModel();
    public ContaSaidaModel contaSaidaModelJason = new ContaSaidaModel();
    public ContaEntradaModel contaEntradaModelJason = new ContaEntradaModel();
    public LancamentoEntradaModel lancaMentoEntradaModelJason = new LancamentoEntradaModel();
    public LancamentoSaidaModel lancamentoSaidaModelJason = new LancamentoSaidaModel();
    public ContaEntradaLancamentosFuturosModel contaEntradaLancamentosFuturosModelJason = new ContaEntradaLancamentosFuturosModel();
    public UserModel userModelJason = new UserModel();


    //Variaveis Lancamento entrada:

    public String identificador;
    public String emailUserLancandoEntrada;
    public String nomeUserLancandoEntrada;
    public String nomeLancamentoEntrada;
    public String dataLancamentoEntrada;
    public String valorLancamentoEntrada;
    public String detalhesLancamentoEntrada;


    //Variaveis Lancamento entrada:

    public String identificadorSaida;
    public String emailUserLancandoSaida;
    public String nomeUserLancandoSaida;
    public String nomeLancamentoSaida;
    public String dataLancamentoSaida;
    public String valorLancamentoSaida;
    public String detalhesLancamentoSaida;

    //Variaveis Cliente Cadatrar/Cadastrado
    public String identificadorCliente;
    public String razaoSocial;
    public String CNPJ;
    public String Usuario;
    public String emailCliente;
    public String telefone;
    public String celular;
    public String OBS;



    //Variaveis Usuario:

    public String user_id;
    public String nomeUser;
    public String emailUser;
    public double valorTotalEntradaMensal;
    public double valorTotalSaidaMensal;
    public int quantidadeTotalLancamentosEntradaMensal;
    public int quantidadeTotalLancamentosSaidaMensal;


    //Variaveis Conta Entrada
    public String idendificadorContaEntrada;
    public String codigoCEntrada;
    public String codigoDEntrada;
    public String descricao;

    //Variaveis Conta Salva Futura;
    private String identificadorContaSalvaFutura;
    private String nomeContaEntradaLancamentoFuturos;
    private String emailClienteQueSugeriu;
    private String identificadorLancamento;

    //Variaveis Conta Saida

    private String idendificadorContaSaida;
    private String codigoCSaida;
    private String codigoDSaida;
    private String fornecedor;
    private String servico;


    //Variaveis Retorno:
    public boolean resultadoLancaEntrada = false;

    //Variareis Teste
    String testeDados;

    //Variaveis helper
    String novoId;
    String mesReferencia;
    String nomeCollectionMesReferencia;
    String mensagemReturn = "";
    int quantidadeRecuperadaLancaSaida;
    int quantidadeRecuperaLancaEntrada;
    double valorRecuperadoLancadoEntrada;
    int contIdContaFutura = 0;


    /*
     * Lancar entrada, usuario irá digitar data, valor lançamento, tipo de lancamento que seria o nome (Ex: Doação, Dizímo, etc..).
     * Talvez seja anonimo esse lancamento e irá precisar ser somado com os valores já acumulado desse usuario
     * */











    //EDITAR LANÇAMENTO DE ENTRADA INICIO METODO
    public String editarLancamentoEntrada(LancamentoEntradaModel lancamentoEntradaModel) {
        return null;
    }
    //EDITAR LANÇAMENTO DE ENTRADA FIM METODO

    //EDITAR LANÇAMENTO DE SAIDA INICIO METODO
    public String editarLancamentoSaida(LancamentoEntradaModel lancamentoEntradaModel) {
        return null;
    }
    //EDITAR LANÇAMENTO SAIDA FIM METODO



    //CADASTRAR CLIENTE INICIO METODO
    public ClienteModel cadastrarCliente(ClienteModel clienteModel) throws ExecutionException, InterruptedException {

        Date data = new Date();
        SimpleDateFormat dataFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

        String dataCreated = dataFormat.format(data);


		/*
		 * 
		 * this.razaoSocial = clienteModel.getRazaoSocial(); this.CNPJ =
		 * clienteModel.getCnpj();
		 */
        this.Usuario = clienteModel.getUsuariocliente();
        this.emailCliente = clienteModel.getEmailCliente();
        this.telefone = clienteModel.getTelefone();
        this.celular = clienteModel.getCelular();
       // this.OBS = clienteModel.getObs();

        if(this.CNPJ != null){
            Firestore firestoreCliente = FirestoreClient.getFirestore();
            DocumentReference documentReferenceCliente;
            documentReferenceCliente = firestoreCliente.collection(NOME_COLLECTION_CLIENTE)
                        .document(this.CNPJ);

            ApiFuture<DocumentSnapshot> documentSnapshotApiFutureCliente = documentReferenceCliente.get();
            DocumentSnapshot documentSnapshotCliente = documentSnapshotApiFutureCliente.get();

            if(documentSnapshotCliente.exists()){
                this.mensagemReturn = "Cliente já tem cadastro";

            }else{

                ClienteModel clienteModelSalva = new ClienteModel();
                //ATRIBUINDO AS VARIAVEIS PARA OS ATRIBUTOS
                clienteModelSalva.setIdentificador(this.CNPJ);
				/*
				 * clienteModelSalva.setRazaoSocial(this.razaoSocial);
				 * clienteModelSalva.setEmailCliente(this.emailCliente);
				 * clienteModelSalva.setCnpj(this.CNPJ);
				 */
                clienteModelSalva.setUsuariocliente(this.Usuario);
                clienteModelSalva.setTelefone(this.telefone);
                clienteModelSalva.setCelular(this.celular);
                clienteModelSalva.setObs("Cliente Criado");
               // clienteModelSalva.setCreated(dataCreated);
                //.setModified("Cliente sem modificação");

                //INSTANCIANDO UM NOVO MAP PARA SER ADICIONADO AO FIREBASE - FIRESTORE
                Map<String , Object> clienteSalva = new HashMap<>();
                //PREPARANDO AS VARIAVEIS PARA SEREM ADICIONADO AO FIREBASE
              //  clienteSalva.put("identificador" , clienteModelSalva.getCnpj());
              //  clienteSalva.put("razaoSocial" , clienteModelSalva.getRazaoSocial());
              //  clienteSalva.put("CNPJ" , clienteModelSalva.getCnpj());
                clienteSalva.put("Usuario" , clienteModelSalva.getUsuariocliente());
                clienteSalva.put("emailCliente" , clienteModelSalva.getEmailCliente());
                clienteSalva.put("telefone" , clienteModelSalva.getTelefone());
                clienteSalva.put("celular" , clienteModelSalva.getCelular());
                clienteSalva.put("OBS" , clienteModelSalva.getObs());
               // clienteSalva.put("created" , clienteModelSalva.getCreated());
               // clienteSalva.put("modified" , clienteModelSalva.getModified());
                //ADICIONADO O LANÇAMENTO QUE ESTÁ SENDO FEITO NO FIREBASE

                Firestore firestoreClienteSalva = FirestoreClient.getFirestore();
                firestoreClienteSalva.collection(NOME_COLLECTION_CLIENTE)
                        .document(clienteModelSalva.getIdentificador())
                        .set(clienteModelSalva);
                this.clienteJason = clienteModelSalva;
            }

        }else{
            this.mensagemReturn = "CNPJ DEU NULO";
        }



       /* this.mensagemReturn = "Razao social: " + this.razaoSocial
                +"CNPJ: " + this.CNPJ
                +"Usuario: " + this.Usuario
                + "email : " + this.emailCliente
                +" telefone: " + this.telefone
                +" celular: " + this.celular
                +" OBS: " + this.OBS;*/
        return this.clienteJason;
    }
    //CADASTRAR CLIENTE FIM METODO

    //EDITAR CLIENTE INICIO METODO
    public String editarCliente(ClienteModel clienteModel) throws ExecutionException, InterruptedException {


        Date data = new Date();
        SimpleDateFormat dataFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

        String dataModified = dataFormat.format(data);

        ClienteModel clienteModelRecuperadoFirebase = new ClienteModel();

        Firestore firestoreCliente = FirestoreClient.getFirestore();
        DocumentReference documentReferenceCliente;
        documentReferenceCliente = firestoreCliente.collection(NOME_COLLECTION_CLIENTE)
				.document(/* clienteModel.getCnpj() */);

        ApiFuture<DocumentSnapshot> usuarioLancaEntradaMesReferencia = documentReferenceCliente.get();
        DocumentSnapshot documentSnapshotUsuarioMesReferencia = usuarioLancaEntradaMesReferencia.get();



        //RECUPERANDO DADOS DO USUARIO PARA AS ATUALIZAÇÕES REFERENTE AO LANÇAMENTO EFETUADO
        ClienteModel clienteModelFirebase = documentSnapshotUsuarioMesReferencia.toObject(ClienteModel.class);


        clienteModelRecuperadoFirebase.setEmailCliente(clienteModelFirebase.getEmailCliente());
       // clienteModelRecuperadoFirebase.setCnpj(clienteModelFirebase.getCnpj());
        clienteModelRecuperadoFirebase.setUsuariocliente(clienteModelFirebase.getUsuariocliente());
        clienteModelRecuperadoFirebase.setCelular(clienteModelFirebase.getCelular());
        clienteModelRecuperadoFirebase.setTelefone(clienteModelFirebase.getTelefone());
        //clienteModelRecuperadoFirebase.setRazaoSocial(clienteModelFirebase.getRazaoSocial());
        clienteModelRecuperadoFirebase.setObs(clienteModelFirebase.getObs());
        clienteModelRecuperadoFirebase.setIdentificador(clienteModelFirebase.getIdentificador());
       // clienteModelRecuperadoFirebase.setCreated(clienteModelFirebase.getCreated());
       // clienteModelRecuperadoFirebase.setModified(dataModified);

        adicionarClienteAlterado(clienteModelFirebase, dataModified);



        this.identificadorCliente = clienteModel.getIdentificador();
       // this.razaoSocial = clienteModel.getRazaoSocial();
       // this.CNPJ = clienteModel.getCnpj();
        this.Usuario = clienteModel.getUsuariocliente();
        this.emailCliente = clienteModel.getEmailCliente();
        this.telefone = clienteModel.getTelefone();
        this.celular = clienteModel.getCelular();
        this.OBS = clienteModel.getObs();


        ClienteModel clienteModelAtualizado = new ClienteModel();

        clienteModelAtualizado.setEmailCliente(this.emailCliente);
       // clienteModelAtualizado.setCnpj(this.CNPJ);
        clienteModelAtualizado.setUsuariocliente(this.Usuario);
        clienteModelAtualizado.setCelular(this.celular);
        clienteModelAtualizado.setTelefone(this.telefone);
      //  clienteModelAtualizado.setRazaoSocial(this.razaoSocial);
        clienteModelAtualizado.setObs("Cliente editado no dia: " + dataModified);
        clienteModelAtualizado.setIdentificador(clienteModelFirebase.getIdentificador());
       // clienteModelAtualizado.setCreated(clienteModelFirebase.getCreated());
       // clienteModelAtualizado.setModified(dataModified);

        documentReferenceCliente.set(clienteModelAtualizado);

this.mensagemReturn = "Cliente alterado";

/*
        if(this.CNPJ != null){
            Firestore firestoreCliente = FirestoreClient.getFirestore();
            DocumentReference documentReferenceCliente;
            documentReferenceCliente = firestoreCliente.collection(NOME_COLLECTION_CLIENTE)
                        .document(this.CNPJ);

            ApiFuture<DocumentSnapshot> usuarioLancaEntradaMesReferencia = documentReferenceCliente.get();
            DocumentSnapshot documentSnapshotUsuarioMesReferencia = usuarioLancaEntradaMesReferencia.get();



            //RECUPERANDO DADOS DO USUARIO PARA AS ATUALIZAÇÕES REFERENTE AO LANÇAMENTO EFETUADO
            ClienteModel clienteModelFirebase = documentSnapshotUsuarioMesReferencia.toObject(ClienteModel.class);




          *//*  if(documentSnapshotCliente.exists()){
                this.mensagemReturn = "Cliente já tem cadastro";

            }
            else{

                ClienteModel clienteModelSalva = new ClienteModel();
                //ATRIBUINDO AS VARIAVEIS PARA OS ATRIBUTOS
                clienteModelSalva.setIdentificador(this.CNPJ);
                clienteModelSalva.setRazaoSocial(this.razaoSocial);
                clienteModelSalva.setEmailCliente(this.emailCliente);
                clienteModelSalva.setCnpj(this.CNPJ);
                clienteModelSalva.setUsuariocliente(this.Usuario);
                clienteModelSalva.setTelefone(this.telefone);
                clienteModelSalva.setCelular(this.celular);
                clienteModelSalva.setObs("Cliente Criado");
                clienteModelSalva.setCreated(dataCreated);
                clienteModelSalva.setModified("Cliente sem modificação");

                //INSTANCIANDO UM NOVO MAP PARA SER ADICIONADO AO FIREBASE - FIRESTORE
                Map<String , Object> clienteSalva = new HashMap<>();
                //PREPARANDO AS VARIAVEIS PARA SEREM ADICIONADO AO FIREBASE
                clienteSalva.put("identificador" , clienteModelSalva.getIdentificador());
                clienteSalva.put("razaoSocial" , clienteModelSalva.getRazaoSocial());
                clienteSalva.put("CNPJ" , clienteModelSalva.getCnpj());
                clienteSalva.put("Usuario" , clienteModelSalva.getUsuariocliente());
                clienteSalva.put("emailCliente" , clienteModelSalva.getEmailCliente());
                clienteSalva.put("telefone" , clienteModelSalva.getTelefone());
                clienteSalva.put("celular" , clienteModelSalva.getCelular());
                clienteSalva.put("OBS" , clienteModelSalva.getObs());
                clienteSalva.put("created" , clienteModelSalva.getCreated());
                clienteSalva.put("modified" , clienteModelSalva.getModified());
                //ADICIONADO O LANÇAMENTO QUE ESTÁ SENDO FEITO NO FIREBASE

                Firestore firestoreClienteSalva = FirestoreClient.getFirestore();
                firestoreClienteSalva.collection(NOME_COLLECTION_CLIENTE)
                        .document(clienteModelSalva.getIdentificador())
                        .set(clienteSalva);
                this.mensagemReturn = "Cliente cadastrado com sucesso";
            }*//*

        }else{
            this.mensagemReturn = "CNPJ DEU NULO";
        }



       *//* this.mensagemReturn = "Razao social: " + this.razaoSocial
                +"CNPJ: " + this.CNPJ
                +"Usuario: " + this.Usuario
                + "email : " + this.emailCliente
                +" telefone: " + this.telefone
                +" celular: " + this.celular
                +" OBS: " + this.OBS;*/
        return this.mensagemReturn;
    }
    //EDITAR CLIENTE FIM METODO

    //EDITAR CLIENTE INICIO METODO
    public ClienteModel recuperarInformacoesCliente(String idUsuarioRecuperaInformacoes ) throws ExecutionException, InterruptedException {

        String idPassado = idUsuarioRecuperaInformacoes;

        if(idPassado != null){

            Firestore firestore = FirestoreClient.getFirestore();
            DocumentReference documentReferenceUsuario = firestore.collection(NOME_COLLECTION_CLIENTE).document(idPassado);

            ApiFuture<DocumentSnapshot> documentSnapshotApiFutureInformacoesUsuarioModel = documentReferenceUsuario.get();
            DocumentSnapshot documentSnapshotInformacoesUserModel = documentSnapshotApiFutureInformacoesUsuarioModel.get();

            if(documentSnapshotInformacoesUserModel.exists()){

                ClienteModel usuarioRecuperadoFirebase = documentSnapshotInformacoesUserModel.toObject(ClienteModel.class);
                this.clienteJason.setIdentificador(usuarioRecuperadoFirebase.getIdentificador());
                this.clienteJason.setUsuariocliente(usuarioRecuperadoFirebase.getUsuariocliente());
                this.clienteJason.setEmailCliente(usuarioRecuperadoFirebase.getEmailCliente());
              //  this.clienteJason.setCnpj(usuarioRecuperadoFirebase.getCnpj());
              //  this.clienteJason.setCreated(usuarioRecuperadoFirebase.getCreated());
               // this.clienteJason.setModified(usuarioRecuperadoFirebase.getModified());
                this.clienteJason.setObs(usuarioRecuperadoFirebase.getObs());
                this.clienteJason.setCelular(usuarioRecuperadoFirebase.getCelular());
                this.clienteJason.setTelefone(usuarioRecuperadoFirebase.getTelefone());
               // this.clienteJason.setRazaoSocial(usuarioRecuperadoFirebase.getRazaoSocial());

            }

        }


        return this.clienteJason;
    }
    //EDITAR CLIENTE FIM METODO

    //ADICIONAR CLIENTE ALTERADO INICIO METODO
    private void adicionarClienteAlterado(ClienteModel clienteModelRecuperadoFirebase, String dataModificacao) {


        Map<String, Object> clienteEditado = new HashMap<>();
        clienteEditado.put("identificador" , clienteModelRecuperadoFirebase.getIdentificador());
		/*
		 * clienteEditado.put("razaoSocial" ,
		 * clienteModelRecuperadoFirebase.getRazaoSocial()); clienteEditado.put("cnpj" ,
		 * clienteModelRecuperadoFirebase.getCnpj());
		 */
        clienteEditado.put("usuariocliente" , clienteModelRecuperadoFirebase.getUsuariocliente());
        clienteEditado.put("emailCliente" , clienteModelRecuperadoFirebase.getEmailCliente());
        clienteEditado.put("telefone" , clienteModelRecuperadoFirebase.getTelefone());
        clienteEditado.put("celular" , clienteModelRecuperadoFirebase.getCelular());
        clienteEditado.put("obs" , clienteModelRecuperadoFirebase.getObs());
      //  clienteEditado.put("created" , clienteModelRecuperadoFirebase.getCreated());
        clienteEditado.put("modified" , dataModificacao);


        Firestore firestoreClienteEditado = FirestoreClient.getFirestore();

        firestoreClienteEditado.collection(NOME_COLLECTION_CLIENTE)
                .document(clienteModelRecuperadoFirebase.getIdentificador())
                .collection("MODIFICAÇOES_CLIENTE")
                .document(dataModificacao)
                .set(clienteEditado);
        this.mensagemReturn = "Cliente antigo salvo com sucesso";

    }
    //ADICIONAR CLIENTE ALTERADO FIM METODO

    //LISTA CLIENTE INICIO METOD
    public List<ClienteModel> getListaClientesCadastrados() throws ExecutionException, InterruptedException {
        List<ClienteModel> resultadoClientesCadastrados = new ArrayList<>();

        Firestore firestoreClientesCadastrados = FirestoreClient.getFirestore();
        CollectionReference collectionReferenceClientesCadastrados = firestoreClientesCadastrados.collection(NOME_COLLECTION_CLIENTE);

        ApiFuture<QuerySnapshot> query = collectionReferenceClientesCadastrados.get();
        List<QueryDocumentSnapshot> documentSnapshots = query.get().getDocuments();
        for(QueryDocumentSnapshot doc : documentSnapshots){
            ClienteModel clienteCadastrado = doc.toObject(ClienteModel.class);
            resultadoClientesCadastrados.add(clienteCadastrado);
        }
        if(resultadoClientesCadastrados.size() == 0){
            return null;
        }else {
            return resultadoClientesCadastrados;
        }


    }
    //LISTA CLIENTE FIM METODO



    //CADATRAR CONTA DE SAIDA INICIO METODO
    public ContaSaidaModel cadastrarContaSaida(ContaSaidaModel contaSaidaModel) throws ExecutionException, InterruptedException {

        Date data = new Date();
        SimpleDateFormat dataFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

        String dataCreated = dataFormat.format(data);

        //this.idendificadorContaEntrada = contaEntradaModel.getIdendificador();
        this.codigoCSaida = contaSaidaModel.getCodigoC();
        this.codigoDSaida = contaSaidaModel.getCodigoD();
        this.fornecedor = contaSaidaModel.getFornecedor();
        this.servico = contaSaidaModel.getServico();



        if(this.codigoCSaida != null && this.codigoDSaida != null && this.fornecedor != null){
            Firestore firestoreContaEntra = FirestoreClient.getFirestore();
            DocumentReference documentReferenceCliente;
            documentReferenceCliente = firestoreContaEntra.collection(NOME_COLLECTION_CONTAS_CONTAS_SAIDA)
                    .document(this.codigoDSaida);

            ApiFuture<DocumentSnapshot> documentSnapshotApiFutureCliente = documentReferenceCliente.get();
            DocumentSnapshot documentSnapshotCliente = documentSnapshotApiFutureCliente.get();

            if(documentSnapshotCliente.exists()){
                this.mensagemReturn = "Cliente já tem cadastro";

            }else{

                this.contaSaidaModelJason = new ContaSaidaModel();
                this.contaSaidaModelJason.setCodigoC(this.codigoCSaida);
                this.contaSaidaModelJason.setCodigoD(this.codigoDSaida);
                this.contaSaidaModelJason.setFornecedor(this.fornecedor);
                this.contaSaidaModelJason.setIdendificador(this.codigoDSaida);
                this.contaSaidaModelJason.setServico(this.servico);
                documentReferenceCliente.set(this.contaSaidaModelJason);

            }

        }else{
            this.mensagemReturn = "CNPJ DEU NULO";
        }



       /* this.mensagemReturn = "Razao social: " + this.razaoSocial
                +"CNPJ: " + this.CNPJ
                +"Usuario: " + this.Usuario
                + "email : " + this.emailCliente
                +" telefone: " + this.telefone
                +" celular: " + this.celular
                +" OBS: " + this.OBS;*/




        return this.contaSaidaModelJason;
    }
    //CADASTRAR CONTA SAIDA FIM METODO



    //SALVAR SUGESTÃO DE CONTA DE ENTRADA PARA LANÇAMENTOS FUTUROS INICIO METODO
    public ContaEntradaLancamentosFuturosModel salvarLancamentosFuturos(ContaEntradaLancamentosFuturosModel contaEntradaLancamentosFuturosModel) throws ExecutionException, InterruptedException {


        this.nomeContaEntradaLancamentoFuturos = contaEntradaLancamentosFuturosModel.getNomeContaEntradaLancamentoFuturos();
        this.emailClienteQueSugeriu = contaEntradaLancamentosFuturosModel.getEmailClienteQueSugeriu();
        this.identificadorLancamento = contaEntradaLancamentosFuturosModel.getIdentificadorCliente();

        String nomeMaiusculo = this.nomeContaEntradaLancamentoFuturos.toUpperCase();
        if(this.emailClienteQueSugeriu != null){

            Firestore firestoreSalvaSugestao = FirestoreClient.getFirestore();
            DocumentReference collectionReferenceSugestao = firestoreSalvaSugestao
                    .collection(NOME_COLLECTION_CLIENTE)
                    .document(contaEntradaLancamentosFuturosModel.getIdentificadorCliente())
                    .collection("LANCAMENTOS_ENTRADA_SALVO_FUTURO")
                    .document(nomeMaiusculo);

            ApiFuture<DocumentSnapshot> usuarioLancaEntrada = collectionReferenceSugestao.get();
            DocumentSnapshot documentSnapshotContaSalvaFutura = usuarioLancaEntrada.get();
            if(documentSnapshotContaSalvaFutura.exists()){

                ContaEntradaLancamentosFuturosModel contaSalva = documentSnapshotContaSalvaFutura.toObject(ContaEntradaLancamentosFuturosModel.class);
                 String identificador = contaSalva.getIdentificador();
                 String nomeContaEntradaLancamentoFuturos = contaSalva.getNomeContaEntradaLancamentoFuturos();
                 String emailClienteQueSugeriu = contaSalva.getEmailClienteQueSugeriu();
                 String identificadorLancamento = contaSalva.getIdentificador();
                 int quantUsa = contaSalva.getQuantasVezesUsadaContaSalvaFuturo()+1;

                this.identificadorContaSalvaFutura = identificador;
                this.nomeContaEntradaLancamentoFuturos = nomeContaEntradaLancamentoFuturos;
                this.emailClienteQueSugeriu = emailClienteQueSugeriu;
                this.identificadorLancamento = identificadorLancamento;

                ContaEntradaLancamentosFuturosModel contaEntradaLancamentosFuturosModePreper = new ContaEntradaLancamentosFuturosModel();

                contaEntradaLancamentosFuturosModePreper.setIdentificador(contaSalva.getIdentificador());
                contaEntradaLancamentosFuturosModePreper.setIdentificadorCliente(contaSalva.getIdentificadorCliente());
                contaEntradaLancamentosFuturosModePreper.setNomeContaEntradaLancamentoFuturos(contaSalva.getNomeContaEntradaLancamentoFuturos());
                contaEntradaLancamentosFuturosModePreper.setEmailClienteQueSugeriu(contaSalva.getEmailClienteQueSugeriu());
                contaEntradaLancamentosFuturosModePreper.setQuantasVezesUsadaContaSalvaFuturo(quantUsa);

                collectionReferenceSugestao.set(contaEntradaLancamentosFuturosModePreper);
                this.contaEntradaLancamentosFuturosModelJason = contaEntradaLancamentosFuturosModePreper;

            }else {

                Firestore firestore = FirestoreClient.getFirestore();
                CollectionReference collectionReference = firestore
                        .collection(NOME_COLLECTION_CLIENTE)
                        .document(contaEntradaLancamentosFuturosModel.getIdentificadorCliente())
                        .collection("LANCAMENTOS_ENTRADA_SALVO_FUTURO");

                this.identificadorContaSalvaFutura = nomeMaiusculo;

                ContaEntradaLancamentosFuturosModel contaEntradaLancamentosFuturosModePreper = new ContaEntradaLancamentosFuturosModel();
                contaEntradaLancamentosFuturosModePreper.setNomeContaEntradaLancamentoFuturos(this.nomeContaEntradaLancamentoFuturos);
                contaEntradaLancamentosFuturosModePreper.setIdentificador(nomeMaiusculo);
                contaEntradaLancamentosFuturosModePreper.setEmailClienteQueSugeriu(this.emailClienteQueSugeriu);
                contaEntradaLancamentosFuturosModePreper.setIdentificadorCliente(this.identificadorLancamento);
                contaEntradaLancamentosFuturosModePreper.setQuantasVezesUsadaContaSalvaFuturo(1);

                this.contaEntradaLancamentosFuturosModelJason = contaEntradaLancamentosFuturosModePreper;
                collectionReference.document(contaEntradaLancamentosFuturosModePreper.getIdentificador()).set(contaEntradaLancamentosFuturosModePreper);

            }



        }



        return this.contaEntradaLancamentosFuturosModelJason;

    }
    //SALVAR SUGESTÃO DE CONTA DE ENTRADA PARA LANÇAMENTOS FUTUROS FIM METODO



    /*
     *ENTRADA, SÃO VALORES QUE OS CLIENTES RECEBEM, OU SEJA, VALORES QUE ENTRAM COMO LUCRO OU ALGO DO TIPO...
     * NO CASO DE IGREJA COMO DOAÇÕES
     *
     *VARIAVEIS LINHA: 39 AO 47
     * CLASSES LINHAS:{
     *  CONTA ENTRADA 32;
     *  LANCAMENTO ENTRADA 33;
     * CONTA ENTRADA LANÇAMENTOS FUTUROS 35;
     * }
     * --------------------------------------------------E N T R A D A S ---- I N I C I O --------------------------------------------------------------------
     * **/


    //CADASTRAR CONTA DE ENTRADA INICIO METODO------------------------------------------------
    public ContaEntradaModel cadastrarContaEntrada(ContaEntradaModel contaEntradaModel) throws ExecutionException, InterruptedException {

        Date data = new Date();
        SimpleDateFormat dataFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

        String dataCreated = dataFormat.format(data);

        //this.idendificadorContaEntrada = contaEntradaModel.getIdendificador();
        this.codigoCEntrada = contaEntradaModel.getCodigoC();
        this.codigoDEntrada = contaEntradaModel.getCodigoD();
        this.descricao = contaEntradaModel.getDescricao();



        if(this.codigoCEntrada != null && this.codigoDEntrada != null && this.descricao != null){
            Firestore firestoreContaEntra = FirestoreClient.getFirestore();
            DocumentReference documentReferenceCliente;
            documentReferenceCliente = firestoreContaEntra.collection(NOME_COLLECTION_CONTAS_CONTAS_ENTRADA)
                    .document(this.codigoCEntrada);

            ApiFuture<DocumentSnapshot> documentSnapshotApiFutureCliente = documentReferenceCliente.get();
            DocumentSnapshot documentSnapshotCliente = documentSnapshotApiFutureCliente.get();

            if(documentSnapshotCliente.exists()){
                this.mensagemReturn = "Cliente já tem cadastro";

            }else{

                this.contaEntradaModelJason = new ContaEntradaModel();
                this.contaEntradaModelJason.setCodigoC(this.codigoCEntrada);
                this.contaEntradaModelJason.setCodigoD(this.codigoDEntrada);
                this.contaEntradaModelJason.setDescricao(this.descricao);
                this.contaEntradaModelJason.setIdendificador(this.codigoCEntrada);
                documentReferenceCliente.set(this.contaEntradaModelJason);


            }

        }else{
            this.mensagemReturn = "CNPJ DEU NULO";
        }



       /* this.mensagemReturn = "Razao social: " + this.razaoSocial
                +"CNPJ: " + this.CNPJ
                +"Usuario: " + this.Usuario
                + "email : " + this.emailCliente
                +" telefone: " + this.telefone
                +" celular: " + this.celular
                +" OBS: " + this.OBS;*/
        return this.contaEntradaModelJason;


    }
    //CADASTRAR CONTA DE ENTRADA FIM METODO---------------------------------------------------

    //LISTA DE LANÇAMENTO DE ENTRADA INICIO METODO-------------------------------------------
    public List<LancamentoEntradaModel> getLancarEntrada() throws ExecutionException, InterruptedException, IOException {

        List<LancamentoEntradaModel> resultado = new ArrayList<>();

        Firestore firestore = FirestoreClient.getFirestore();
        CollectionReference collectionReference = firestore.collection(NOME_COLLECTION_ACUMULADOS);

        ApiFuture<QuerySnapshot> query = collectionReference.get();
        List<QueryDocumentSnapshot> documentSnapshots = query.get().getDocuments();
        for(QueryDocumentSnapshot doc : documentSnapshots){
            LancamentoEntradaModel entradaSalda = doc.toObject(LancamentoEntradaModel.class);
            resultado.add(entradaSalda);
        }
        return resultado;
    /*public LancamentoSaidaModel getLancarSaida(String collection) throws ExecutionException, InterruptedException {

        Firestore firestore = FirestoreClient.getFirestore();
        DocumentReference documentReference = firestore.collection(NOME_COLLECTION_ACUMULADOS_ENTRADA).document(collection).collection("LANCAMENTOS_ENTRADA_DIARIA").document();
        ApiFuture<DocumentSnapshot> listaEntradaLancada = documentReference.get();
        DocumentSnapshot documentSnapshot = listaEntradaLancada.get();

        LancamentoSaidaModel lancamentoSaidaModel;
        if(documentSnapshot.exists()){
            lancamentoSaidaModel  = documentSnapshot.toObject(LancamentoSaidaModel.class);
            return lancamentoSaidaModel;
        }

        return null;
    }

    public String deletarSaidaLancada(String collection) {
        return "";
    }

    public String deletarEntradaLancada(String collection) {
        return "";
    }*/
    }
    //LISTA DE LANÇAMENTO DE ENTRADA FIM DE METODO-------------------------------------------




    //INICIO METODO--------------------------------------------------------------------------
    public LancamentoEntradaModel lancarEntrada(LancamentoEntradaModel lancamentoEntradaModel) throws ExecutionException, InterruptedException {

        //Recuperando as informações

        this.identificador = lancamentoEntradaModel.getIdentificador();
        this.dataLancamentoEntrada = lancamentoEntradaModel.getDataLancamentoEntrada();
        this.emailUserLancandoEntrada = lancamentoEntradaModel.getEmailUserLancandoEntrada();
        this.valorLancamentoEntrada = lancamentoEntradaModel.getValorLancamentoEntrada();



        //Configurando
        String [] dataRecebida = this.dataLancamentoEntrada.split("-");
        String dataFormatadaLancamentoEntrada = dataRecebida[2]+ "/" +dataRecebida[1]+ "/" +dataRecebida[0];
        String anoCollection = dataRecebida[0];
        int valorMesReferenciaLancado = Integer.parseInt(dataRecebida[1]);

        switch (valorMesReferenciaLancado){
            case 1:
                this.mesReferencia = "JANEIRO";
                break;
            case 2:
                this.mesReferencia = "FEVEREIRO";
                break;
            case 3:
                this.mesReferencia = "MARÇO";
                break;
            case 4:
                this.mesReferencia = "ABRIL";
                break;
            case 5:
                this.mesReferencia = "MAIO";
                break;
            case 6:
                this.mesReferencia = "JUNHO";
                break;
            case 7:
                this.mesReferencia = "JULHO";
                break;
            case 8:
                this.mesReferencia = "AGOSTO";
                break;
            case 9:
                this.mesReferencia = "SETEMBRO";
                break;
            case 10:
                this.mesReferencia = "OUTUBRO";
                break;
            case 11:
                this.mesReferencia = "NOVEMBRO";
                break;
            case 12:
                this.mesReferencia = "DEZEMBRO";
                break;

            default:
                break;

        }


        this.nomeCollectionMesReferencia = "ACUMULADO_MES_"+this.mesReferencia;

        String valorLancamentoEntradaRecebido = this.valorLancamentoEntrada;
        String valorLancamentoEntradaLimpo = valorLancamentoEntradaRecebido.replace(",", ".");
        double valorLancamentoEntradaConvertido = Double.parseDouble(valorLancamentoEntradaLimpo);





        if(this.emailUserLancandoEntrada != null){

            String nomeUsuario = lancamentoEntradaModel.getNomeUserLancandoEntrada();
            String nomeMaiusculo = nomeUsuario.toUpperCase();
           /* String nomeCollectionAcumuladoUsuario = "ACUMULADO_"+nomeMaiusculo;*/


            Firestore firestoreCollectionMesReferencia = FirestoreClient.getFirestore();
            DocumentReference documentReferenceusuario = firestoreCollectionMesReferencia.collection(NOME_COLLECTION_CLIENTE)
                    .document(this.identificador)
                    .collection("ACUMULADO")
                    .document(anoCollection)
                    .collection(this.mesReferencia)
                    .document(this.nomeCollectionMesReferencia);

            ApiFuture<DocumentSnapshot> usuarioLancaEntrada = documentReferenceusuario.get();
            DocumentSnapshot documentSnapshotUsuario = usuarioLancaEntrada.get();

            if(documentSnapshotUsuario.exists()){

                /*
                 * CASO O DOCUMENTOUSUARIO EXISTA, SIGNIFICA QUE O USUARIO JÁ EFETOU LANÇAMENTO ANTES, ENTÃO NO CASO SERÁ ATUALIZAÇÕES
                 */

                Firestore firestoreAcumuldoMesReferencia = FirestoreClient.getFirestore();
                DocumentReference documentReferenceusuarioMesReferencia = firestoreAcumuldoMesReferencia
                        .collection(NOME_COLLECTION_CLIENTE)
                        .document(this.identificador)
                        .collection("ACUMULADO")
                        .document(anoCollection)
                        .collection(this.mesReferencia)
                        .document(this.nomeCollectionMesReferencia);

                ApiFuture<DocumentSnapshot> usuarioLancaEntradaMesReferencia = documentReferenceusuarioMesReferencia.get();
                DocumentSnapshot documentSnapshotUsuarioMesReferencia = usuarioLancaEntradaMesReferencia.get();



                //RECUPERANDO DADOS DO USUARIO PARA AS ATUALIZAÇÕES REFERENTE AO LANÇAMENTO EFETUADO
                UserModel ValoresUserModelLancaEntrada = documentSnapshotUsuarioMesReferencia.toObject(UserModel.class);


                //VARIAVEIS LOCAIS RECEBENDO OS VALORES DO LANCAMENTO EFETUADO PELO USUARIO EM QUESTÃO
                this.identificador = lancamentoEntradaModel.getIdentificador();
                this.nomeUserLancandoEntrada = lancamentoEntradaModel.getNomeUserLancandoEntrada();
                this.nomeLancamentoEntrada = lancamentoEntradaModel.getNomeLancamentoEntrada();
                this.dataLancamentoEntrada = lancamentoEntradaModel.getDataLancamentoEntrada();
                this.valorLancamentoEntrada = lancamentoEntradaModel.getValorLancamentoEntrada();
                this.detalhesLancamentoEntrada = lancamentoEntradaModel.getDetalhesLancamentoEntrada();


                //VALORES RECUPERADOS DO USUARIO PARA PREPARAÇÃO DAS DEVIDAS ATUALIZAÇÕES:

                assert ValoresUserModelLancaEntrada != null;
                double valorTotalEntradaMensal = ValoresUserModelLancaEntrada.getValorTotalEntradaMensal();

                //PREPARANDO AS VARIAVEIS LOCAIS COM AS TEMPORARIAS PARA ATUALIZAR UM NOVO USERMODEL
                this.user_id = this.emailUserLancandoEntrada;
                this.nomeUser = ValoresUserModelLancaEntrada.getNomeUser();
                this.emailUser = ValoresUserModelLancaEntrada.getEmailUser();
                double resultado = valorLancamentoEntradaConvertido + valorTotalEntradaMensal;
                this.valorTotalEntradaMensal = resultado;
                this.valorTotalSaidaMensal = ValoresUserModelLancaEntrada.getValorTotalSaidaMensal();
                this.quantidadeTotalLancamentosEntradaMensal = ValoresUserModelLancaEntrada.getQuantidadeTotalLancamentosEntradaMensal() + 1;
                this.quantidadeTotalLancamentosSaidaMensal = ValoresUserModelLancaEntrada.getQuantidadeTotalLancamentosSaidaMensal();

                //NOVO USUARIO SENTO INSTANCIADO PARA RECEBER VALORES JÁ TRATADOS-----------------------------
                UserModel usuarioAtualiza = new UserModel();
                //ATRIBUINDO AS VARIAVEIS PARA OS ATRIBUTOS---------------------------------------------------
                usuarioAtualiza.setUser_id(this.user_id);
                usuarioAtualiza.setNomeUser(this.nomeUser);
                usuarioAtualiza.setEmailUser(this.emailUser);
                usuarioAtualiza.setValorTotalEntradaMensal(this.valorTotalEntradaMensal);
                usuarioAtualiza.setValorTotalSaidaMensal(this.valorTotalSaidaMensal);
                usuarioAtualiza.setQuantidadeTotalLancamentosEntradaMensal(this.quantidadeTotalLancamentosEntradaMensal);
                usuarioAtualiza.setQuantidadeTotalLancamentosSaidaMensal(this.quantidadeTotalLancamentosSaidaMensal);

                //NOVO LANCAMENTOMODEL SENTO INSTANCIADO PARA RECEBER VALORES JÁ TRATADOS------------
                LancamentoEntradaModel lancamentoEntradaModelSalva = new LancamentoEntradaModel();
                //ATRIBUINDO AS VARIAVEIS PARA OS ATRIBUTOS------------------------------------------
                lancamentoEntradaModelSalva.setIdentificador(String.valueOf(this.quantidadeTotalLancamentosEntradaMensal));
                lancamentoEntradaModelSalva.setEmailUserLancandoEntrada( this.emailUserLancandoEntrada);
                lancamentoEntradaModelSalva.setNomeUserLancandoEntrada(this.nomeUserLancandoEntrada);
                lancamentoEntradaModelSalva.setNomeLancamentoEntrada(this.nomeLancamentoEntrada);
                lancamentoEntradaModelSalva.setDataLancamentoEntrada(dataFormatadaLancamentoEntrada);
                lancamentoEntradaModelSalva.setValorLancamentoEntrada(this.valorLancamentoEntrada);
                lancamentoEntradaModelSalva.setDetalhesLancamentoEntrada(this.detalhesLancamentoEntrada);

                this.lancaMentoEntradaModelJason = lancamentoEntradaModelSalva;
                //CHAMANDO O METODO PARA ATUALIZAR
                atualizaValorTotalEntradaMensalUsuario(this.nomeCollectionMesReferencia, anoCollection, usuarioAtualiza,lancamentoEntradaModelSalva);

                this.mensagemReturn = "Sucesso ao adicionar novo lancamento entrada";
                this.resultadoLancaEntrada = true;

            }else{
                /*
                 * CASO O DOCUMENTOUSUARIO NÃO EXISTA, SIGNIFICA QUE O USUARIO NUNCA EFETOU LANÇAMENTO ANTES, ENTÃO NO CASO
                 * SERÁ UMA INICIALIZAÇÃO DE LANCAMENTO DE ENTRDA
                 */

                this.nomeUserLancandoEntrada = lancamentoEntradaModel.getNomeUserLancandoEntrada();
                this.nomeLancamentoEntrada = lancamentoEntradaModel.getNomeLancamentoEntrada();
                this.dataLancamentoEntrada = lancamentoEntradaModel.getDataLancamentoEntrada();
                this.valorLancamentoEntrada = lancamentoEntradaModel.getValorLancamentoEntrada();
                this.detalhesLancamentoEntrada = lancamentoEntradaModel.getDetalhesLancamentoEntrada();

                //CHAMANDO O METODO PARA ADICIONAR O LANCAMENTO QUE ESTÁ SENDO FEITO PELO USUARIO PELA 1º VEZ
                adicionaLancamentoUsuario(this.nomeCollectionMesReferencia, anoCollection, this.emailUserLancandoEntrada,
                        this.nomeUserLancandoEntrada,
                        this.nomeLancamentoEntrada,
                        this.dataLancamentoEntrada,
                        this.valorLancamentoEntrada,
                        this.detalhesLancamentoEntrada);

                //NOVO USUARIO SENTO INSTANCIADO PARA RECEBER VALORES INICIAIS
                UserModel userModelNovo = new UserModel();
                //ATRIBUINDO AS VARIAVEIS PARA OS ATRIBUTOS
                userModelNovo.setEmailUser(this.emailUserLancandoEntrada);
                userModelNovo.setNomeUser(this.nomeUserLancandoEntrada);
                userModelNovo.setValorTotalEntradaMensal(valorLancamentoEntradaConvertido);
                userModelNovo.setValorTotalSaidaMensal(0);
                userModelNovo.setQuantidadeTotalLancamentosEntradaMensal(1);
                userModelNovo.setQuantidadeTotalLancamentosSaidaMensal(0);

                //INSTANCIANDO UM NOVO MAP PARA SER ADICIONADO AO FIREBASE - FIRESTORE
                Map<String , Object> dadosSalva = new HashMap<>();
                //PREPARANDO AS VARIAVEIS PARA SEREM ADICIONADO AO FIREBASE
                dadosSalva.put("nomeUser" , userModelNovo.getNomeUser());
                dadosSalva.put("emailUser" , userModelNovo.getEmailUser());
                dadosSalva.put("valorTotalEntradaMensal" , userModelNovo.getValorTotalEntradaMensal());
                dadosSalva.put("valorTotalSaidaMensal" , userModelNovo.getValorTotalSaidaMensal());
                dadosSalva.put("quantidadeTotalLancamentosEntradaMensal" , userModelNovo.getQuantidadeTotalLancamentosEntradaMensal());
                dadosSalva.put("quantidadeTotalLancamentosSaidaMensal" , userModelNovo.getQuantidadeTotalLancamentosSaidaMensal());
                //ADICIONADO O LANÇAMENTO QUE ESTÁ SENDO FEITO NO FIREBASE

                firestoreCollectionMesReferencia.collection(NOME_COLLECTION_CLIENTE)
                        .document(this.identificador)
                        .collection("ACUMULADO")
                        .document(anoCollection)
                        .collection(this.mesReferencia)
                        .document(this.nomeCollectionMesReferencia)
                        .set(userModelNovo);

                this.lancaMentoEntradaModelJason = lancamentoEntradaModel;
                this.mensagemReturn = "Sucesso ao criar novo lancamento entrada";
                this.resultadoLancaEntrada = true;
            }
            //QUANDO ACABAR E CASO NÃO RETORNE ERRO SERÁ ATRIBUIDO VERDADEIRO PARA A VARIAVEIS DE RETORNO DO METODO

        }



        return this.lancaMentoEntradaModelJason;
    }
    //FIM METODO--------------------------------------------------------------------------

    public LancamentoSaidaModel lancarSaida(LancamentoSaidaModel lancamentoEntradaModel) throws ExecutionException, InterruptedException {

        //Recuperando as informações


        this.dataLancamentoSaida = lancamentoEntradaModel.getDataLancamentoSaida();
        this.emailUserLancandoSaida = lancamentoEntradaModel.getEmailUserLancandoSaida();
        this.valorLancamentoSaida = lancamentoEntradaModel.getValorLancamentoSaida();
        this.nomeUserLancandoSaida = lancamentoEntradaModel.getNomeUserLancandoSaida();


        //Configurando
        String [] dataRecebida = this.dataLancamentoSaida.split("-");
        String dataFormatadaLancamentoEntrada = dataRecebida[2]+ "/" +dataRecebida[1]+ "/" +dataRecebida[0];
        String anoCollection = dataRecebida[0];

        int valorMesReferenciaLancado = Integer.parseInt(dataRecebida[1]);

        switch (valorMesReferenciaLancado){
            case 1:
                this.mesReferencia = "JANEIRO";
                break;
            case 2:
                this.mesReferencia = "FEVEREIRO";
                break;
            case 3:
                this.mesReferencia = "MARÇO";
                break;
            case 4:
                this.mesReferencia = "ABRIL";
                break;
            case 5:
                this.mesReferencia = "MAIO";
                break;
            case 6:
                this.mesReferencia = "JUNHO";
                break;
            case 7:
                this.mesReferencia = "JULHO";
                break;
            case 8:
                this.mesReferencia = "AGOSTO";
                break;
            case 9:
                this.mesReferencia = "SETEMBRO";
                break;
            case 10:
                this.mesReferencia = "OUTUBRO";
                break;
            case 11:
                this.mesReferencia = "NOVEMBRO";
                break;
            case 12:
                this.mesReferencia = "DEZEMBRO";
                break;

            default:
                break;

        }


        this.nomeCollectionMesReferencia = "ACUMULADO_MES_"+this.mesReferencia;

        String valorLancamentoEntradaRecebido = this.valorLancamentoSaida;
        String valorLancamentoEntradaLimpo = valorLancamentoEntradaRecebido.replace(",", ".");
        double valorLancamentoEntradaConvertido = Double.parseDouble(valorLancamentoEntradaLimpo);





        if(this.emailUserLancandoSaida != null){

            String nomeUsuario = lancamentoEntradaModel.getNomeUserLancandoSaida();
            String nomeMaiusculo = nomeUsuario.toUpperCase();
            String nomeCollectionAcumuladoUsuario = "ACUMULADO_"+nomeMaiusculo;


            Firestore firestoreCollectionMesReferencia = FirestoreClient.getFirestore();
            DocumentReference documentReferenceusuario = firestoreCollectionMesReferencia.collection(NOME_COLLECTION_CLIENTE)
                    .document(this.identificador)
                    .collection("ACUMULADO")
                    .document(anoCollection)
                    .collection(this.mesReferencia)
                    .document(this.nomeCollectionMesReferencia);

            ApiFuture<DocumentSnapshot> usuarioLancaEntrada = documentReferenceusuario.get();
            DocumentSnapshot documentSnapshotUsuario = usuarioLancaEntrada.get();

            if(documentSnapshotUsuario.exists()){

                /*
                 * CASO O DOCUMENTOUSUARIO EXISTA, SIGNIFICA QUE O USUARIO JÁ EFETOU LANÇAMENTO ANTES, ENTÃO NO CASO SERÁ ATUALIZAÇÕES
                 */

                Firestore firestoreAcumuldoMesReferencia = FirestoreClient.getFirestore();
                DocumentReference documentReferenceusuarioMesReferencia = firestoreAcumuldoMesReferencia.collection(NOME_COLLECTION_CLIENTE)
                        .document(this.identificador)
                        .collection("ACUMULADO")
                        .document(anoCollection)
                        .collection(this.mesReferencia)
                        .document(this.nomeCollectionMesReferencia);
                ApiFuture<DocumentSnapshot> usuarioLancaEntradaMesReferencia = documentReferenceusuarioMesReferencia.get();
                DocumentSnapshot documentSnapshotUsuarioMesReferencia = usuarioLancaEntradaMesReferencia.get();



                //RECUPERANDO DADOS DO USUARIO PARA AS ATUALIZAÇÕES REFERENTE AO LANÇAMENTO EFETUADO
                UserModel ValoresUserModelLancaEntrada = documentSnapshotUsuarioMesReferencia.toObject(UserModel.class);


                //VARIAVEIS LOCAIS RECEBENDO OS VALORES DO LANCAMENTO EFETUADO PELO USUARIO EM QUESTÃO
                this.identificador = lancamentoEntradaModel.getIdentificador();
                this.nomeUserLancandoEntrada = lancamentoEntradaModel.getNomeUserLancandoSaida();
               // this.nomeLancamentoEntrada = lancamentoEntradaModel.getNomeLancamentoSaida();
                this.dataLancamentoEntrada = lancamentoEntradaModel.getDataLancamentoSaida();
                this.valorLancamentoEntrada = lancamentoEntradaModel.getValorLancamentoSaida();
                //this.detalhesLancamentoEntrada = lancamentoEntradaModel.getDetalhesLancamentoSaida();


                //VALORES RECUPERADOS DO USUARIO PARA PREPARAÇÃO DAS DEVIDAS ATUALIZAÇÕES:

                assert ValoresUserModelLancaEntrada != null;
                double valorTotalSaidaMensal = ValoresUserModelLancaEntrada.getValorTotalSaidaMensal();

                //PREPARANDO AS VARIAVEIS LOCAIS COM AS TEMPORARIAS PARA ATUALIZAR UM NOVO USERMODEL
                this.user_id = this.emailUserLancandoSaida;
                this.nomeUser = ValoresUserModelLancaEntrada.getNomeUser();
                this.emailUser = ValoresUserModelLancaEntrada.getEmailUser();
                double resultado = valorLancamentoEntradaConvertido + valorTotalSaidaMensal;
                this.valorTotalSaidaMensal = resultado;
                this.valorTotalEntradaMensal = ValoresUserModelLancaEntrada.getValorTotalEntradaMensal();
                this.quantidadeTotalLancamentosEntradaMensal = ValoresUserModelLancaEntrada.getQuantidadeTotalLancamentosEntradaMensal() ;
                this.quantidadeTotalLancamentosSaidaMensal = ValoresUserModelLancaEntrada.getQuantidadeTotalLancamentosSaidaMensal()+ 1;

                //NOVO USUARIO SENTO INSTANCIADO PARA RECEBER VALORES JÁ TRATADOS-----------------------------
                UserModel usuarioAtualiza = new UserModel();
                //ATRIBUINDO AS VARIAVEIS PARA OS ATRIBUTOS---------------------------------------------------
                usuarioAtualiza.setUser_id(this.user_id);
                usuarioAtualiza.setNomeUser(this.nomeUser);
                usuarioAtualiza.setEmailUser(this.emailUser);
                usuarioAtualiza.setValorTotalEntradaMensal(this.valorTotalEntradaMensal);
                usuarioAtualiza.setValorTotalSaidaMensal(this.valorTotalSaidaMensal);
                usuarioAtualiza.setQuantidadeTotalLancamentosEntradaMensal(this.quantidadeTotalLancamentosEntradaMensal);
                usuarioAtualiza.setQuantidadeTotalLancamentosSaidaMensal(this.quantidadeTotalLancamentosSaidaMensal);

                //NOVO LANCAMENTOMODEL SENTO INSTANCIADO PARA RECEBER VALORES JÁ TRATADOS------------
                LancamentoSaidaModel lancamentoEntradaModelSalva = new LancamentoSaidaModel();
                //ATRIBUINDO AS VARIAVEIS PARA OS ATRIBUTOS------------------------------------------
                lancamentoEntradaModelSalva.setIdentificador(this.identificador);
                lancamentoEntradaModelSalva.setEmailUserLancandoSaida( this.emailUserLancandoSaida);
                lancamentoEntradaModelSalva.setNomeUserLancandoSaida(this.nomeUserLancandoEntrada);
               // lancamentoEntradaModelSalva.setNomeLancamentoSaida(this.nomeLancamentoEntrada);
                lancamentoEntradaModelSalva.setDataLancamentoSaida(dataFormatadaLancamentoEntrada);
                lancamentoEntradaModelSalva.setValorLancamentoSaida(this.valorLancamentoEntrada);
                //lancamentoEntradaModelSalva.setDetalhesLancamentoSaida(this.detalhesLancamentoEntrada);

                this.lancamentoSaidaModelJason = lancamentoEntradaModelSalva;
                //CHAMANDO O METODO PARA ATUALIZAR
                atualizaValorTotalSaidaMensalUsuario(this.nomeCollectionMesReferencia, anoCollection,usuarioAtualiza,lancamentoEntradaModelSalva);

                this.mensagemReturn = "Sucesso ao adicionar novo lancamento entrada";
                this.resultadoLancaEntrada = true;

            }else{
                /*
                 * CASO O DOCUMENTOUSUARIO NÃO EXISTA, SIGNIFICA QUE O USUARIO NUNCA EFETOU LANÇAMENTO ANTES, ENTÃO NO CASO
                 * SERÁ UMA INICIALIZAÇÃO DE LANCAMENTO DE ENTRDA
                 */

                this.nomeUserLancandoEntrada = lancamentoEntradaModel.getNomeUserLancandoSaida();
               // this.nomeLancamentoEntrada = lancamentoEntradaModel.getNomeLancamentoSaida();
                this.dataLancamentoEntrada = lancamentoEntradaModel.getDataLancamentoSaida();
                this.valorLancamentoEntrada = lancamentoEntradaModel.getValorLancamentoSaida();
               // this.detalhesLancamentoEntrada = lancamentoEntradaModel.getDetalhesLancamentoSaida();

                //CHAMANDO O METODO PARA ADICIONAR O LANCAMENTO QUE ESTÁ SENDO FEITO PELO USUARIO PELA 1º VEZ
                adicionaLancamentoSaidaUsuario(this.emailUserLancandoEntrada,
                        this.nomeCollectionMesReferencia, anoCollection,
                        this.nomeUserLancandoEntrada,
                        this.nomeLancamentoEntrada,
                        this.dataLancamentoEntrada,
                        this.valorLancamentoEntrada,
                        this.detalhesLancamentoEntrada);

                //NOVO USUARIO SENTO INSTANCIADO PARA RECEBER VALORES INICIAIS
                UserModel userModelNovo = new UserModel();
                //ATRIBUINDO AS VARIAVEIS PARA OS ATRIBUTOS
                userModelNovo.setEmailUser(this.emailUserLancandoEntrada);
                userModelNovo.setNomeUser(this.nomeUserLancandoEntrada);
                userModelNovo.setValorTotalEntradaMensal(0);
                userModelNovo.setValorTotalSaidaMensal(valorLancamentoEntradaConvertido);
                userModelNovo.setQuantidadeTotalLancamentosEntradaMensal(0);
                userModelNovo.setQuantidadeTotalLancamentosSaidaMensal(1);

                //INSTANCIANDO UM NOVO MAP PARA SER ADICIONADO AO FIREBASE - FIRESTORE
                Map<String , Object> dadosSalva = new HashMap<>();
                //PREPARANDO AS VARIAVEIS PARA SEREM ADICIONADO AO FIREBASE
                dadosSalva.put("nomeUser" , userModelNovo.getNomeUser());
                dadosSalva.put("emailUser" , userModelNovo.getEmailUser());
                dadosSalva.put("valorTotalEntradaMensal" , userModelNovo.getValorTotalEntradaMensal());
                dadosSalva.put("valorTotalSaidaMensal" , userModelNovo.getValorTotalSaidaMensal());
                dadosSalva.put("quantidadeTotalLancamentosEntradaMensal" , userModelNovo.getQuantidadeTotalLancamentosEntradaMensal());
                dadosSalva.put("quantidadeTotalLancamentosSaidaMensal" , userModelNovo.getQuantidadeTotalLancamentosSaidaMensal());
                //ADICIONADO O LANÇAMENTO QUE ESTÁ SENDO FEITO NO FIREBASE
                firestoreCollectionMesReferencia.collection(NOME_COLLECTION_CLIENTE)
                        .document(this.identificador)
                        .collection("ACUMULADO")
                        .document(anoCollection)
                        .collection(this.mesReferencia)
                        .document(this.nomeCollectionMesReferencia)
                        .set(userModelNovo);

                this.lancamentoSaidaModelJason = lancamentoEntradaModel;
                this.mensagemReturn = "Sucesso ao criar novo lancamento entrada";
                this.resultadoLancaEntrada = true;
            }
            //QUANDO ACABAR E CASO NÃO RETORNE ERRO SERÁ ATRIBUIDO VERDADEIRO PARA A VARIAVEIS DE RETORNO DO METODO

        }



        return this.lancamentoSaidaModelJason;
    }


    //INICIO METODO--------------------------------------------------------------------------
    private void adicionaLancamentoUsuario(String nomeCollectionMesReferencia,
                                           String anoReferencia,
                                           String emailUserLancandoEntrada,
                                           String nomeUserLancandoEntrada,
                                           String nomeLancamentoEntrada,
                                           String dataLancamentoEntrada,
                                           String valorLancamentoEntrada,
                                           String detalhesLancamentoEntrada) {


        String nomeUsuario = nomeUserLancandoEntrada;
        String nomeMaiusculo = nomeUsuario.toUpperCase();
        String nomeCollectionMesReferenciaAcumuladoUsuario = nomeCollectionMesReferencia;


        //INSTANCIANDO UMA NOVA REFERENCIA AO FIREBASE CONECTADO
        Firestore firestore = FirestoreClient.getFirestore();

        //-------------INICIO DATA FORMATADA PARA CRIAÇÃO-------------------------//
        Date data = new Date();
        SimpleDateFormat dataFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

        String dataCreated = dataFormat.format(data);

        //-------------FIM DATA FORMATADA PARA CRIAÇÃO-------------------------//


        //Valores para Lancamento Entrada
        String [] dataRecebida = dataLancamentoEntrada.split("-");
        String dataFormatadaLancamentoEntrada = dataRecebida[2]+ "/" +dataRecebida[1]+ "/" +dataRecebida[0];

        //INSTANCIANDO  NOVO LANCAMENTO DE ENTRADA
        LancamentoEntradaModel lancamentoEntradaModelSalva = new LancamentoEntradaModel();
        //ATRIBUINDO OS PARAMETROS RECEBIDOS AOS ATRIBUTOS DO NOVO LANCAMENTO DE ENTRADA INSTANCIADO
        lancamentoEntradaModelSalva.setIdentificador("1");
        lancamentoEntradaModelSalva.setEmailUserLancandoEntrada(emailUserLancandoEntrada);
        lancamentoEntradaModelSalva.setNomeUserLancandoEntrada(nomeUserLancandoEntrada);
        lancamentoEntradaModelSalva.setNomeLancamentoEntrada(nomeLancamentoEntrada);
        lancamentoEntradaModelSalva.setDataLancamentoEntrada(dataFormatadaLancamentoEntrada);
        lancamentoEntradaModelSalva.setValorLancamentoEntrada(valorLancamentoEntrada);
        lancamentoEntradaModelSalva.setDetalhesLancamentoEntrada(detalhesLancamentoEntrada);
        lancamentoEntradaModelSalva.setCreatedLancamentoEntrada(dataCreated);
        lancamentoEntradaModelSalva.setModifieldLancamentoEntrada("Nenhuma Modificação");

        //INSTANCIANDO 1º OBJETO DO TIPO MAP PARA SER ADICIONADO AO FIRESTORE DO FIREBASE
        Map<String , Object> LancamentoEntrada = new HashMap<>();
        //ATRIBUINDO OS VALORES DO 1º OBJETO LANCAMENTO DE ENTRADA AOS VALORES DOS ATRIBUTOS DO 1º OBJETO MAP
        LancamentoEntrada.put("identificador" , "1");
        LancamentoEntrada.put("emailUserLancandoEntrada" , lancamentoEntradaModelSalva.getEmailUserLancandoEntrada());
        LancamentoEntrada.put("nomeUserLancandoEntrada" , lancamentoEntradaModelSalva.getNomeUserLancandoEntrada());
        LancamentoEntrada.put("nomeLancamentoEntrada" , lancamentoEntradaModelSalva.getNomeLancamentoEntrada());
        LancamentoEntrada.put("dataLancamentoEntrada" , lancamentoEntradaModelSalva.getDataLancamentoEntrada());
        LancamentoEntrada.put("valorLancamentoEntrada" , lancamentoEntradaModelSalva.getValorLancamentoEntrada());
        LancamentoEntrada.put("detalhesLancamentoEntrada" , lancamentoEntradaModelSalva.getDetalhesLancamentoEntrada());
        LancamentoEntrada.put("createdLancamentoEntrada" , lancamentoEntradaModelSalva.getCreatedLancamentoEntrada());
        LancamentoEntrada.put("modifieldLancamentoEntrada" , lancamentoEntradaModelSalva.getModifieldLancamentoEntrada());


        //CRIANDO O 1º NÓ DE LANCAMENTO DE ENTRADA DO USUARIO E ADICIONADO O OBJETO DO TIPO MAP AO FIRESTORE DO FIREBASE
        firestore.collection(NOME_COLLECTION_CLIENTE)
                .document(this.identificador)
                .collection("ACUMULADO")
                .document(anoReferencia)
                .collection(this.mesReferencia)
                .document(nomeCollectionMesReferenciaAcumuladoUsuario)
                .collection(NOME_COLLECTION_LANCAMENTO_ENTRADA_DIARIA_USUARIO)
                .document("1")
                .set(lancamentoEntradaModelSalva);


    }

    //FIM METODO------------------------------------------------------------------------------

    //INICIO METODO
    private void adicionaLancamentoSaidaUsuario(String nomeCollectionMesReferencia,
                                                String anoReferencia,
                                                String emailUserLancandoEntrada,
                                                String nomeUserLancandoEntrada,
                                                String nomeLancamentoEntrada,
                                                String dataLancamentoEntrada,
                                                String valorLancamentoEntrada,
                                                String detalhesLancamentoEntrada) {

        String nomeUsuario = nomeUserLancandoEntrada;
        String nomeMaiusculo = nomeUsuario.toUpperCase();
        String nomeCollectionMesReferenciaAcumuladoUsuario = nomeCollectionMesReferencia;


        //INSTANCIANDO UMA NOVA REFERENCIA AO FIREBASE CONECTADO
        Firestore firestore = FirestoreClient.getFirestore();

        //-------------INICIO DATA FORMATADA PARA CRIAÇÃO-------------------------//
        Date data = new Date();
        SimpleDateFormat dataFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

        String dataCreated = dataFormat.format(data);

        //-------------FIM DATA FORMATADA PARA CRIAÇÃO-------------------------//


        //Valores para Lancamento Entrada
        String [] dataRecebida = dataLancamentoEntrada.split("-");
        String dataFormatadaLancamentoEntrada = dataRecebida[2]+ "/" +dataRecebida[1]+ "/" +dataRecebida[0];

        //INSTANCIANDO  NOVO LANCAMENTO DE ENTRADA
        LancamentoSaidaModel lancamentoEntradaModelSalva = new LancamentoSaidaModel();
        //ATRIBUINDO OS PARAMETROS RECEBIDOS AOS ATRIBUTOS DO NOVO LANCAMENTO DE ENTRADA INSTANCIADO
        lancamentoEntradaModelSalva.setIdentificador("1");
        lancamentoEntradaModelSalva.setEmailUserLancandoSaida(emailUserLancandoEntrada);
        lancamentoEntradaModelSalva.setNomeUserLancandoSaida(nomeUserLancandoEntrada);
       // lancamentoEntradaModelSalva.setNomeLancamentoSaida(nomeLancamentoEntrada);
        lancamentoEntradaModelSalva.setDataLancamentoSaida(dataFormatadaLancamentoEntrada);
        lancamentoEntradaModelSalva.setValorLancamentoSaida(valorLancamentoEntrada);
       // lancamentoEntradaModelSalva.setDetalhesLancamentoSaida(detalhesLancamentoEntrada);
        lancamentoEntradaModelSalva.setCreatedLancamentoSaida(dataCreated);
        lancamentoEntradaModelSalva.setModifieldLancamentoSaida("Nenhuma Modificação");

        //INSTANCIANDO 1º OBJETO DO TIPO MAP PARA SER ADICIONADO AO FIRESTORE DO FIREBASE
        Map<String , Object> LancamentoEntrada = new HashMap<>();
        //ATRIBUINDO OS VALORES DO 1º OBJETO LANCAMENTO DE SAIDA AOS VALORES DOS ATRIBUTOS DO 1º OBJETO MAP
        LancamentoEntrada.put("identificador" , "1");
        LancamentoEntrada.put("emailUserLancandoSaida" , lancamentoEntradaModelSalva.getEmailUserLancandoSaida());
        LancamentoEntrada.put("nomeUserLancandoSaida" , lancamentoEntradaModelSalva.getNomeUserLancandoSaida());
      //  LancamentoEntrada.put("nomeLancamentoSaida" , lancamentoEntradaModelSalva.getNomeLancamentoSaida());
        LancamentoEntrada.put("dataLancamentoSaida" , lancamentoEntradaModelSalva.getDataLancamentoSaida());
        LancamentoEntrada.put("valorLancamentoSaida" , lancamentoEntradaModelSalva.getValorLancamentoSaida());
       // LancamentoEntrada.put("detalhesLancamentoSaida" , lancamentoEntradaModelSalva.getDetalhesLancamentoSaida());
        LancamentoEntrada.put("createdLancamentoSaida" , lancamentoEntradaModelSalva.getCreatedLancamentoSaida());
        LancamentoEntrada.put("modifieldLancamentoSaida" , lancamentoEntradaModelSalva.getModifieldLancamentoSaida());


        //CRIANDO O 1º NÓ DE LANCAMENTO DE ENTRADA DO USUARIO E ADICIONADO O OBJETO DO TIPO MAP AO FIRESTORE DO FIREBASE
        firestore.collection(NOME_COLLECTION_CLIENTE)
                .document(this.identificador)
                .collection("ACUMULADO")
                .document(anoReferencia)
                .collection(this.mesReferencia)
                .document(nomeCollectionMesReferenciaAcumuladoUsuario)
                .collection(NOME_COLLECTION_LANCAMENTO_SAIDA_DIARIA_USUARIO)
                .document("1")
                .set(lancamentoEntradaModelSalva);


    }

    //INICIO METODO



    //INICIO METODO--------------------------------------------------------------------------
    private void atualizaValorTotalEntradaMensalUsuario(String nomeCollectionMesReferencia,
                                                        String anoReferencia, UserModel usuarioAtualiza,
                                                        LancamentoEntradaModel lancamentoEntradaModelSalva) {


        String nomeUsuario = usuarioAtualiza.getNomeUser();
        String nomeMaiusculo = nomeUsuario.toUpperCase();
        String nomeCollectionAcumuladoUsuario = nomeCollectionMesReferencia;
        String anoColletcion = anoReferencia;



        //-------------INICIO DATA FORMATADA PARA CRIAÇÃO-------------------------//
        Date data = new Date();
        SimpleDateFormat dataFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

        String dataCreated = dataFormat.format(data);

        //-------------FIM DATA FORMATADA PARA CRIAÇÃO-------------------------//


        //-------------INICIO INSTANCIANDO FIRESTORE-------------------------//


        Firestore firestore = FirestoreClient.getFirestore();
        //firestore.collection(NOME_COLLECTION_ACUMULADOS).document(lancamentoEntradaModelSalva.getEmailUserLancandoEntrada()).collection(NOME_COLLECTION_LANCAMENTO_ENTRADA_DIARIA_USUARIO);

        //-------------FIM INSTANCIANDO FIRESTORE-------------------------//


        //-------------INICIO PREPARACAO DAS VARIAVEIRS DO LANCAMENTO PARA ATUALIZACAO NO FIRESTORE-------------------------//

        this.novoId = String.valueOf(usuarioAtualiza.getValorTotalEntradaMensal());
        this.testeDados = this.novoId;

        LancamentoEntradaModel lancamentoSalvo = new LancamentoEntradaModel();

        lancamentoSalvo.setIdentificador(lancamentoEntradaModelSalva.getIdentificador());
        lancamentoSalvo.setEmailUserLancandoEntrada(lancamentoEntradaModelSalva.getEmailUserLancandoEntrada());
        lancamentoSalvo.setNomeUserLancandoEntrada(lancamentoEntradaModelSalva.getNomeUserLancandoEntrada());
        lancamentoSalvo.setNomeLancamentoEntrada(lancamentoEntradaModelSalva.getNomeLancamentoEntrada());
        lancamentoSalvo.setDetalhesLancamentoEntrada(lancamentoEntradaModelSalva.getDetalhesLancamentoEntrada());
        lancamentoSalvo.setValorLancamentoEntrada(lancamentoEntradaModelSalva.getValorLancamentoEntrada());
        lancamentoSalvo.setDataLancamentoEntrada(lancamentoEntradaModelSalva.getDataLancamentoEntrada());
        lancamentoSalvo.setCreatedLancamentoEntrada(dataCreated);
        lancamentoSalvo.setModifieldLancamentoEntrada("Nenhuma Modificação");


        Map<String , Object> LancamentoEntrada = new HashMap<>();

        LancamentoEntrada.put("identificador" , lancamentoSalvo.getIdentificador());
        LancamentoEntrada.put("emailUserLancandoEntrada" , lancamentoSalvo.getEmailUserLancandoEntrada());
        LancamentoEntrada.put("nomeUserLancandoEntrada" , lancamentoSalvo.getNomeUserLancandoEntrada());
        LancamentoEntrada.put("nomeLancamentoEntrada" , lancamentoSalvo.getNomeLancamentoEntrada());
        LancamentoEntrada.put("dataLancamentoEntrada" , lancamentoSalvo.getDataLancamentoEntrada());
        LancamentoEntrada.put("valorLancamentoEntrada" , lancamentoSalvo.getValorLancamentoEntrada());
        LancamentoEntrada.put("detalhesLancamentoEntrada" , lancamentoSalvo.getDetalhesLancamentoEntrada());
        LancamentoEntrada.put("createdLancamentoEntrada" , lancamentoSalvo.getCreatedLancamentoEntrada());
        LancamentoEntrada.put("modifieldLancamentoEntrada" , lancamentoSalvo.getModifieldLancamentoEntrada());


        firestore.collection(NOME_COLLECTION_CLIENTE)
                .document(this.identificador)
                .collection("ACUMULADO")
                .document(anoReferencia)
                .collection(this.mesReferencia)
                .document(nomeCollectionAcumuladoUsuario)
                .collection(NOME_COLLECTION_LANCAMENTO_ENTRADA_DIARIA_USUARIO)
                .document(lancamentoSalvo.getIdentificador())
                .set(lancamentoSalvo);


/*
        firestore.collection(NOME_COLLECTION_ACUMULADOS)
                .document(usuarioAtualiza.getEmailUser())
                .collection(NOME_COLLECTION_ACUMULADOS_ENTRADA_USUARIO)
                .document(this.nomeCollectionMesReferencia)
                .collection(NOME_COLLECTION_LANCAMENTO_ENTRADA_DIARIA_USUARIO)
                .document(lancamentoSalvo.getIdentificador())
                .set(LancamentoEntrada);*/
        //-------------FIM PREPARACAO DAS VARIAVEIRS DO LANCAMENTO PARA ATUALIZACAO NO FIRESTORE-------------------------//


        //-------------INICIO PREPARACAO DAS VARIAVEIRS DO USUARIO PARA ATUALIZACAO NO FIRESTORE-------------------------//



        Map<String , Object> dadosSalva = new HashMap<>();

        dadosSalva.put("nomeUser" , usuarioAtualiza.getNomeUser());
        dadosSalva.put("emailUser" , usuarioAtualiza.getEmailUser());
        dadosSalva.put("valorTotalEntradaMensal" , usuarioAtualiza.getValorTotalEntradaMensal());
        dadosSalva.put("valorTotalSaidaMensal" , usuarioAtualiza.getValorTotalSaidaMensal());
        dadosSalva.put("quantidadeTotalLancamentosEntradaMensal" , usuarioAtualiza.getQuantidadeTotalLancamentosEntradaMensal());
        dadosSalva.put("quantidadeTotalLancamentosSaidaMensal" , usuarioAtualiza.getQuantidadeTotalLancamentosSaidaMensal());

        firestore.collection(NOME_COLLECTION_CLIENTE)
                .document(this.identificador)
                .collection("ACUMULADO")
                .document(anoColletcion)
                .collection(this.mesReferencia)
                .document(this.nomeCollectionMesReferencia)
                .set(dadosSalva);

        //-------------FIM PREPARACAO DAS VARIAVEIRS DO USUARIO PARA ATUALIZACAO NO FIRESTORE-------------------------//


    }
    //FIM METODO--------------------------------------------------------------------------




    private void atualizaValorTotalSaidaMensalUsuario(String nomeCollectionMesReferencia,
                                                      String anoReferencia,UserModel usuarioAtualiza,
                                                      LancamentoSaidaModel lancamentoEntradaModelSalva) throws ExecutionException, InterruptedException {


        String nomeUsuario = usuarioAtualiza.getNomeUser();
        String nomeMaiusculo = nomeUsuario.toUpperCase();
        String nomeCollectionAcumuladoUsuario = nomeCollectionMesReferencia;
        String anoColletcion = anoReferencia;




        //-------------INICIO DATA FORMATADA PARA CRIAÇÃO-------------------------//
        Date data = new Date();
        SimpleDateFormat dataFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

        String dataCreated = dataFormat.format(data);

        //-------------FIM DATA FORMATADA PARA CRIAÇÃO-------------------------//


        //-------------INICIO INSTANCIANDO FIRESTORE-------------------------//


        Firestore firestore = FirestoreClient.getFirestore();
        //firestore.collection(NOME_COLLECTION_ACUMULADOS).document(lancamentoEntradaModelSalva.getEmailUserLancandoEntrada()).collection(NOME_COLLECTION_LANCAMENTO_ENTRADA_DIARIA_USUARIO);

        //-------------FIM INSTANCIANDO FIRESTORE-------------------------//


        //-------------INICIO PREPARACAO DAS VARIAVEIRS DO LANCAMENTO PARA ATUALIZACAO NO FIRESTORE-------------------------//

        this.novoId = String.valueOf(usuarioAtualiza.getValorTotalSaidaMensal());
        this.testeDados = this.novoId;

        LancamentoEntradaModel lancamentoSalvo = new LancamentoEntradaModel();

        lancamentoSalvo.setIdentificador(this.novoId);
        lancamentoSalvo.setEmailUserLancandoEntrada(lancamentoEntradaModelSalva.getEmailUserLancandoSaida());
        lancamentoSalvo.setNomeUserLancandoEntrada(lancamentoEntradaModelSalva.getNomeUserLancandoSaida());
       // lancamentoSalvo.setNomeLancamentoEntrada(lancamentoEntradaModelSalva.getNomeLancamentoSaida());
       // lancamentoSalvo.setDetalhesLancamentoEntrada(lancamentoEntradaModelSalva.getDetalhesLancamentoSaida());
        lancamentoSalvo.setValorLancamentoEntrada(lancamentoEntradaModelSalva.getValorLancamentoSaida());
        //lancamentoSalvo.setDataLancamentoEntrada(lancamentoEntradaModelSalva.getDetalhesLancamentoSaida());
        lancamentoSalvo.setCreatedLancamentoEntrada(dataCreated);
        lancamentoSalvo.setModifieldLancamentoEntrada("Nenhuma Modificação");


        Map<String , Object> LancamentoEntrada = new HashMap<>();

        LancamentoEntrada.put("identificador" , lancamentoSalvo.getIdentificador());
        LancamentoEntrada.put("emailUserLancandoEntrada" , lancamentoSalvo.getEmailUserLancandoEntrada());
        LancamentoEntrada.put("nomeUserLancandoEntrada" , lancamentoSalvo.getNomeUserLancandoEntrada());
        LancamentoEntrada.put("nomeLancamentoEntrada" , lancamentoSalvo.getNomeLancamentoEntrada());
        LancamentoEntrada.put("dataLancamentoEntrada" , lancamentoSalvo.getDataLancamentoEntrada());
        LancamentoEntrada.put("valorLancamentoEntrada" , lancamentoSalvo.getValorLancamentoEntrada());
        LancamentoEntrada.put("detalhesLancamentoEntrada" , lancamentoSalvo.getDetalhesLancamentoEntrada());
        LancamentoEntrada.put("createdLancamentoEntrada" , lancamentoSalvo.getCreatedLancamentoEntrada());
        LancamentoEntrada.put("modifieldLancamentoEntrada" , lancamentoSalvo.getModifieldLancamentoEntrada());


        firestore.collection(NOME_COLLECTION_CLIENTE)
                .document(this.identificador)
                .collection("ACUMULADO")
                .document(anoReferencia)
                .collection(this.mesReferencia)
                .document(nomeCollectionAcumuladoUsuario)
                .collection(NOME_COLLECTION_LANCAMENTO_SAIDA_DIARIA_USUARIO)
                .document(lancamentoSalvo.getIdentificador())
                .set(LancamentoEntrada);


/*
        firestore.collection(NOME_COLLECTION_ACUMULADOS)
                .document(usuarioAtualiza.getEmailUser())
                .collection(NOME_COLLECTION_ACUMULADOS_ENTRADA_USUARIO)
                .document(this.nomeCollectionMesReferencia)
                .collection(NOME_COLLECTION_LANCAMENTO_ENTRADA_DIARIA_USUARIO)
                .document(lancamentoSalvo.getIdentificador())
                .set(LancamentoEntrada);*/
        //-------------FIM PREPARACAO DAS VARIAVEIRS DO LANCAMENTO PARA ATUALIZACAO NO FIRESTORE-------------------------//


        //-------------INICIO PREPARACAO DAS VARIAVEIRS DO USUARIO PARA ATUALIZACAO NO FIRESTORE-------------------------//



        Map<String , Object> dadosSalva = new HashMap<>();

        dadosSalva.put("nomeUser" , usuarioAtualiza.getNomeUser());
        dadosSalva.put("emailUser" , usuarioAtualiza.getEmailUser());
        dadosSalva.put("valorTotalEntradaMensal" , usuarioAtualiza.getValorTotalEntradaMensal());
        dadosSalva.put("valorTotalSaidaMensal" , usuarioAtualiza.getValorTotalSaidaMensal());
        dadosSalva.put("quantidadeTotalLancamentosEntradaMensal" , usuarioAtualiza.getQuantidadeTotalLancamentosEntradaMensal());
        dadosSalva.put("quantidadeTotalLancamentosSaidaMensal" , usuarioAtualiza.getQuantidadeTotalLancamentosSaidaMensal());

        firestore.collection(NOME_COLLECTION_CLIENTE)
                .document(this.identificador)
                .collection("ACUMULADO")
                .document(anoColletcion)
                .collection(this.mesReferencia)
                .document(this.nomeCollectionMesReferencia)
                .set(dadosSalva);

        //-------------FIM PREPARACAO DAS VARIAVEIRS DO USUARIO PARA ATUALIZACAO NO FIRESTORE-------------------------//

    }
//FIM METODO



    //LISTA CONTA ENTRADA INICIO METODO
    public List<ContaEntradaModel> getListaContasEntradaCadastradas() throws ExecutionException, InterruptedException {

        List<ContaEntradaModel> resultadoListaContasEntradaCadastradas = new ArrayList<>();

        Firestore firestore = FirestoreClient.getFirestore();
        CollectionReference collectionReference = firestore.collection(NOME_COLLECTION_CONTAS_CONTAS_ENTRADA);

        ApiFuture<QuerySnapshot> query = collectionReference.get();
        List<QueryDocumentSnapshot> documentSnapshots = query.get().getDocuments();
        for(QueryDocumentSnapshot doc : documentSnapshots){
            ContaEntradaModel contaEntradaCadastradaBanco = doc.toObject(ContaEntradaModel.class);
            resultadoListaContasEntradaCadastradas.add(contaEntradaCadastradaBanco);
        }
        return resultadoListaContasEntradaCadastradas;
    }
    //LISTA CONTA ENTRADA FIM METODO




    //RECUPERAR VALORES DO MÊS DE JANEIRO  INICIO METODO
    public UserModel recuperarValoresUsuarioJaneiro(UserModel userModel)throws ExecutionException, InterruptedException{

            this.emailCliente = userModel.getEmailUser();
            this.nomeUser = userModel.getNomeUser();


        String nomeMaiusculo = this.nomeUser.toUpperCase();
        String nomeCollectionAcumuladoUsuario = "ACUMULADO_"+nomeMaiusculo;

        if(this.emailCliente != null){

            Firestore firebaseDadosJaneiro = FirestoreClient.getFirestore();
            DocumentReference collectionReferenceSugestao = firebaseDadosJaneiro.collection(NOME_COLLECTION_ACUMULADOS).document(this.emailCliente)
                    .collection(nomeCollectionAcumuladoUsuario)
                    .document("ACUMULADO_MES_JANEIRO");


            ApiFuture<DocumentSnapshot> dadosJaneiro = collectionReferenceSugestao.get();
            DocumentSnapshot documentSnapshotDadosJaneiro = dadosJaneiro.get();
            if(documentSnapshotDadosJaneiro.exists()){

                UserModel userModelFirebase = documentSnapshotDadosJaneiro.toObject(UserModel.class);
                this.userModelJason.setNomeUser(userModelFirebase.getNomeUser());
                this.userModelJason.setEmailUser(userModelFirebase.getEmailUser());
                this.userModelJason.setValorTotalSaidaMensal(userModelFirebase.getValorTotalSaidaMensal());
                this.userModelJason.setValorTotalEntradaMensal(userModelFirebase.getValorTotalEntradaMensal());
                this.userModelJason.setQuantidadeTotalLancamentosSaidaMensal(userModelFirebase.getQuantidadeTotalLancamentosSaidaMensal());
                this.userModelJason.setQuantidadeTotalLancamentosEntradaMensal(userModelFirebase.getQuantidadeTotalLancamentosEntradaMensal());

            }else{
                UserModel semDados = new UserModel();
                semDados.setValorTotalSaidaMensal(0);
                semDados.setValorTotalEntradaMensal(0);
                semDados.setQuantidadeTotalLancamentosSaidaMensal(0);
                semDados.setQuantidadeTotalLancamentosEntradaMensal(0);


                this.userModelJason.setValorTotalSaidaMensal(semDados.getValorTotalSaidaMensal());
                this.userModelJason.setValorTotalEntradaMensal(semDados.getValorTotalEntradaMensal());
                this.userModelJason.setQuantidadeTotalLancamentosSaidaMensal(semDados.getQuantidadeTotalLancamentosSaidaMensal());
                this.userModelJason.setQuantidadeTotalLancamentosEntradaMensal(semDados.getQuantidadeTotalLancamentosEntradaMensal());

            }

            }
return this.userModelJason;


    }
    //RECUPERAR VALORES DO MÊS DE JANEIRO  FIM METODO

    //RECUPERAR VALORES DO MÊS DE DEZEMBRO  INICIO METODO
    public UserModel getAcumuladoReferenciaDezembro(UserModel userModel) throws ExecutionException, InterruptedException {

        this.emailCliente = userModel.getEmailUser();
        this.nomeUser = userModel.getNomeUser();


        String nomeMaiusculo = this.nomeUser.toUpperCase();
        String nomeCollectionAcumuladoUsuario = "ACUMULADO_"+nomeMaiusculo;

        if(this.emailCliente != null){

            Firestore firebaseDadosJaneiro = FirestoreClient.getFirestore();
            DocumentReference collectionReferenceSugestao = firebaseDadosJaneiro.collection(NOME_COLLECTION_ACUMULADOS).document(this.emailCliente)
                    .collection(nomeCollectionAcumuladoUsuario)
                    .document("ACUMULADO_MES_DEZEMBRO");


            ApiFuture<DocumentSnapshot> dadosJaneiro = collectionReferenceSugestao.get();
            DocumentSnapshot documentSnapshotDadosJaneiro = dadosJaneiro.get();
            if(documentSnapshotDadosJaneiro.exists()){

                UserModel userModelFirebase = documentSnapshotDadosJaneiro.toObject(UserModel.class);
                this.userModelJason.setNomeUser(userModelFirebase.getNomeUser());
                this.userModelJason.setEmailUser(userModelFirebase.getEmailUser());
                this.userModelJason.setValorTotalSaidaMensal(userModelFirebase.getValorTotalSaidaMensal());
                this.userModelJason.setValorTotalEntradaMensal(userModelFirebase.getValorTotalEntradaMensal());
                this.userModelJason.setQuantidadeTotalLancamentosSaidaMensal(userModelFirebase.getQuantidadeTotalLancamentosSaidaMensal());
                this.userModelJason.setQuantidadeTotalLancamentosEntradaMensal(userModelFirebase.getQuantidadeTotalLancamentosEntradaMensal());

            }else{
                UserModel semDados = new UserModel();
                semDados.setValorTotalSaidaMensal(0);
                semDados.setValorTotalEntradaMensal(0);
                semDados.setQuantidadeTotalLancamentosSaidaMensal(0);
                semDados.setQuantidadeTotalLancamentosEntradaMensal(0);


                this.userModelJason.setValorTotalSaidaMensal(semDados.getValorTotalSaidaMensal());
                this.userModelJason.setValorTotalEntradaMensal(semDados.getValorTotalEntradaMensal());
                this.userModelJason.setQuantidadeTotalLancamentosSaidaMensal(semDados.getQuantidadeTotalLancamentosSaidaMensal());
                this.userModelJason.setQuantidadeTotalLancamentosEntradaMensal(semDados.getQuantidadeTotalLancamentosEntradaMensal());

            }

        }
        return this.userModelJason;
    }
    //RECUPERAR VALORES DO MÊS DE DEZEMBRO  FIM METODO

    //RECUPERAR VALORES DO MÊS DE NOVEMBRO INICIO METODO
    public UserModel getAcumuladoReferenciaNovembro(UserModel userModel) throws ExecutionException, InterruptedException {


        this.emailCliente = userModel.getEmailUser();
        this.nomeUser = userModel.getNomeUser();


        String nomeMaiusculo = this.nomeUser.toUpperCase();
        String nomeCollectionAcumuladoUsuario = "ACUMULADO_"+nomeMaiusculo;

        if(this.emailCliente != null){

            Firestore firebaseDadosJaneiro = FirestoreClient.getFirestore();
            DocumentReference collectionReferenceSugestao = firebaseDadosJaneiro.collection(NOME_COLLECTION_ACUMULADOS).document(this.emailCliente)
                    .collection(nomeCollectionAcumuladoUsuario)
                    .document("ACUMULADO_MES_NOVEMBRO");


            ApiFuture<DocumentSnapshot> dadosJaneiro = collectionReferenceSugestao.get();
            DocumentSnapshot documentSnapshotDadosJaneiro = dadosJaneiro.get();
            if(documentSnapshotDadosJaneiro.exists()){

                UserModel userModelFirebase = documentSnapshotDadosJaneiro.toObject(UserModel.class);
                this.userModelJason.setNomeUser(userModelFirebase.getNomeUser());
                this.userModelJason.setEmailUser(userModelFirebase.getEmailUser());
                this.userModelJason.setValorTotalSaidaMensal(userModelFirebase.getValorTotalSaidaMensal());
                this.userModelJason.setValorTotalEntradaMensal(userModelFirebase.getValorTotalEntradaMensal());
                this.userModelJason.setQuantidadeTotalLancamentosSaidaMensal(userModelFirebase.getQuantidadeTotalLancamentosSaidaMensal());
                this.userModelJason.setQuantidadeTotalLancamentosEntradaMensal(userModelFirebase.getQuantidadeTotalLancamentosEntradaMensal());

            }else{
                UserModel semDados = new UserModel();
                semDados.setValorTotalSaidaMensal(0);
                semDados.setValorTotalEntradaMensal(0);
                semDados.setQuantidadeTotalLancamentosSaidaMensal(0);
                semDados.setQuantidadeTotalLancamentosEntradaMensal(0);


                this.userModelJason.setValorTotalSaidaMensal(semDados.getValorTotalSaidaMensal());
                this.userModelJason.setValorTotalEntradaMensal(semDados.getValorTotalEntradaMensal());
                this.userModelJason.setQuantidadeTotalLancamentosSaidaMensal(semDados.getQuantidadeTotalLancamentosSaidaMensal());
                this.userModelJason.setQuantidadeTotalLancamentosEntradaMensal(semDados.getQuantidadeTotalLancamentosEntradaMensal());

        }

        }
        return this.userModelJason;
    }
    //RECUPERAR VALORES DO MÊS DE NOVEMBRO  FIM METODO

    //RECUPERAR VALORES DO MÊS DE OUTUBRO USUARIO INICIO METODO
    public UserModel getAcumuladoReferenciaOutubro(UserModel userModel) throws ExecutionException, InterruptedException {


        this.emailCliente = userModel.getEmailUser();
        this.nomeUser = userModel.getNomeUser();


        String nomeMaiusculo = this.nomeUser.toUpperCase();
        String nomeCollectionAcumuladoUsuario = "ACUMULADO_"+nomeMaiusculo;

        if(this.emailCliente != null){

            Firestore firebaseDadosJaneiro = FirestoreClient.getFirestore();
            DocumentReference collectionReferenceSugestao = firebaseDadosJaneiro.collection(NOME_COLLECTION_ACUMULADOS).document(this.emailCliente)
                    .collection(nomeCollectionAcumuladoUsuario)
                    .document("ACUMULADO_MES_OUTUBRO");


            ApiFuture<DocumentSnapshot> dadosJaneiro = collectionReferenceSugestao.get();
            DocumentSnapshot documentSnapshotDadosJaneiro = dadosJaneiro.get();
            if(documentSnapshotDadosJaneiro.exists()){

                UserModel userModelFirebase = documentSnapshotDadosJaneiro.toObject(UserModel.class);
                this.userModelJason.setNomeUser(userModelFirebase.getNomeUser());
                this.userModelJason.setEmailUser(userModelFirebase.getEmailUser());
                this.userModelJason.setValorTotalSaidaMensal(userModelFirebase.getValorTotalSaidaMensal());
                this.userModelJason.setValorTotalEntradaMensal(userModelFirebase.getValorTotalEntradaMensal());
                this.userModelJason.setQuantidadeTotalLancamentosSaidaMensal(userModelFirebase.getQuantidadeTotalLancamentosSaidaMensal());
                this.userModelJason.setQuantidadeTotalLancamentosEntradaMensal(userModelFirebase.getQuantidadeTotalLancamentosEntradaMensal());

            }else{
                UserModel semDados = new UserModel();
                semDados.setValorTotalSaidaMensal(0);
                semDados.setValorTotalEntradaMensal(0);
                semDados.setQuantidadeTotalLancamentosSaidaMensal(0);
                semDados.setQuantidadeTotalLancamentosEntradaMensal(0);


                this.userModelJason.setValorTotalSaidaMensal(semDados.getValorTotalSaidaMensal());
                this.userModelJason.setValorTotalEntradaMensal(semDados.getValorTotalEntradaMensal());
                this.userModelJason.setQuantidadeTotalLancamentosSaidaMensal(semDados.getQuantidadeTotalLancamentosSaidaMensal());
                this.userModelJason.setQuantidadeTotalLancamentosEntradaMensal(semDados.getQuantidadeTotalLancamentosEntradaMensal());

        }

    } return this.userModelJason;
    }
    //RECUPERAR VALORES DO MÊS DE OUTUBRO  FIM METODO

    //RECUPERAR VALORES DO MÊS DE FEVEREIRO  INICIO METODO
    public UserModel getAcumuladoReferenciaFevereiro(UserModel userModel) throws ExecutionException, InterruptedException {


        this.emailCliente = userModel.getEmailUser();
        String nomeUsuario = userModel.getNomeUser();


        String nomeMaiusculo = nomeUsuario.toUpperCase();
        String nomeCollectionAcumuladoUsuario = "ACUMULADO_" + nomeMaiusculo;

        if (this.emailCliente != null) {

            Firestore firebaseDadosJaneiro = FirestoreClient.getFirestore();
            DocumentReference collectionReferenceSugestao = firebaseDadosJaneiro.collection(NOME_COLLECTION_ACUMULADOS).document(this.emailCliente)
                    .collection(nomeCollectionAcumuladoUsuario)
                    .document("ACUMULADO_MES_FEVEREIRO");


            ApiFuture<DocumentSnapshot> dadosJaneiro = collectionReferenceSugestao.get();
            DocumentSnapshot documentSnapshotDadosJaneiro = dadosJaneiro.get();
            if (documentSnapshotDadosJaneiro.exists()) {

                UserModel userModelFirebase = documentSnapshotDadosJaneiro.toObject(UserModel.class);
                this.userModelJason.setNomeUser(userModelFirebase.getNomeUser());
                this.userModelJason.setEmailUser(userModelFirebase.getEmailUser());
                this.userModelJason.setValorTotalSaidaMensal(userModelFirebase.getValorTotalSaidaMensal());
                this.userModelJason.setValorTotalEntradaMensal(userModelFirebase.getValorTotalEntradaMensal());
                this.userModelJason.setQuantidadeTotalLancamentosSaidaMensal(userModelFirebase.getQuantidadeTotalLancamentosSaidaMensal());
                this.userModelJason.setQuantidadeTotalLancamentosEntradaMensal(userModelFirebase.getQuantidadeTotalLancamentosEntradaMensal());

            } else {
                UserModel semDados = new UserModel();
                semDados.setValorTotalSaidaMensal(0);
                semDados.setValorTotalEntradaMensal(0);
                semDados.setQuantidadeTotalLancamentosSaidaMensal(0);
                semDados.setQuantidadeTotalLancamentosEntradaMensal(0);


                this.userModelJason.setValorTotalSaidaMensal(semDados.getValorTotalSaidaMensal());
                this.userModelJason.setValorTotalEntradaMensal(semDados.getValorTotalEntradaMensal());
                this.userModelJason.setQuantidadeTotalLancamentosSaidaMensal(semDados.getQuantidadeTotalLancamentosSaidaMensal());
                this.userModelJason.setQuantidadeTotalLancamentosEntradaMensal(semDados.getQuantidadeTotalLancamentosEntradaMensal());

            }

        }return this.userModelJason;
    }
    //RECUPERAR VALORES DO MÊS DE  FEVEREIRO FIM METODO

    //RECUPERAR VALORES DO MÊS DE MARÇO USUARIO INICIO METODO
    public UserModel getAcumuladoReferenciaMarco(UserModel userModel) throws ExecutionException, InterruptedException {


        this.emailCliente = userModel.getEmailUser();
        this.nomeUser = userModel.getNomeUser();


        String nomeMaiusculo = this.nomeUser.toUpperCase();
        String nomeCollectionAcumuladoUsuario = "ACUMULADO_"+nomeMaiusculo;

        if(this.emailCliente != null){

            Firestore firebaseDadosJaneiro = FirestoreClient.getFirestore();
            DocumentReference collectionReferenceSugestao = firebaseDadosJaneiro.collection(NOME_COLLECTION_ACUMULADOS).document(this.emailCliente)
                    .collection(nomeCollectionAcumuladoUsuario)
                    .document("ACUMULADO_MES_MARCO");


            ApiFuture<DocumentSnapshot> dadosJaneiro = collectionReferenceSugestao.get();
            DocumentSnapshot documentSnapshotDadosJaneiro = dadosJaneiro.get();
            if(documentSnapshotDadosJaneiro.exists()){

                UserModel userModelFirebase = documentSnapshotDadosJaneiro.toObject(UserModel.class);
                this.userModelJason.setNomeUser(userModelFirebase.getNomeUser());
                this.userModelJason.setEmailUser(userModelFirebase.getEmailUser());
                this.userModelJason.setValorTotalSaidaMensal(userModelFirebase.getValorTotalSaidaMensal());
                this.userModelJason.setValorTotalEntradaMensal(userModelFirebase.getValorTotalEntradaMensal());
                this.userModelJason.setQuantidadeTotalLancamentosSaidaMensal(userModelFirebase.getQuantidadeTotalLancamentosSaidaMensal());
                this.userModelJason.setQuantidadeTotalLancamentosEntradaMensal(userModelFirebase.getQuantidadeTotalLancamentosEntradaMensal());

            }else{
                UserModel semDados = new UserModel();
                semDados.setValorTotalSaidaMensal(0);
                semDados.setValorTotalEntradaMensal(0);
                semDados.setQuantidadeTotalLancamentosSaidaMensal(0);
                semDados.setQuantidadeTotalLancamentosEntradaMensal(0);


                this.userModelJason.setValorTotalSaidaMensal(semDados.getValorTotalSaidaMensal());
                this.userModelJason.setValorTotalEntradaMensal(semDados.getValorTotalEntradaMensal());
                this.userModelJason.setQuantidadeTotalLancamentosSaidaMensal(semDados.getQuantidadeTotalLancamentosSaidaMensal());
                this.userModelJason.setQuantidadeTotalLancamentosEntradaMensal(semDados.getQuantidadeTotalLancamentosEntradaMensal());

            }

        }
        return this.userModelJason;
    }
    //RECUPERAR VALORES DO MÊS DE MARÇO  FIM METODO

    //RECUPERAR VALORES DO MÊS DE ABRIL USUARIO INICIO METODO
    public UserModel getAcumuladoReferenciaAbril(UserModel userModel) throws ExecutionException, InterruptedException {

        this.emailCliente = userModel.getEmailUser();
        this.nomeUser = userModel.getNomeUser();


        String nomeMaiusculo = this.nomeUser.toUpperCase();
        String nomeCollectionAcumuladoUsuario = "ACUMULADO_"+nomeMaiusculo;

        if(this.emailCliente != null){

            Firestore firebaseDadosJaneiro = FirestoreClient.getFirestore();
            DocumentReference collectionReferenceSugestao = firebaseDadosJaneiro.collection(NOME_COLLECTION_ACUMULADOS).document(this.emailCliente)
                    .collection(nomeCollectionAcumuladoUsuario)
                    .document("ACUMULADO_MES_ABRIL");


            ApiFuture<DocumentSnapshot> dadosJaneiro = collectionReferenceSugestao.get();
            DocumentSnapshot documentSnapshotDadosJaneiro = dadosJaneiro.get();
            if(documentSnapshotDadosJaneiro.exists()){

                UserModel userModelFirebase = documentSnapshotDadosJaneiro.toObject(UserModel.class);
                this.userModelJason.setNomeUser(userModelFirebase.getNomeUser());
                this.userModelJason.setEmailUser(userModelFirebase.getEmailUser());
                this.userModelJason.setValorTotalSaidaMensal(userModelFirebase.getValorTotalSaidaMensal());
                this.userModelJason.setValorTotalEntradaMensal(userModelFirebase.getValorTotalEntradaMensal());
                this.userModelJason.setQuantidadeTotalLancamentosSaidaMensal(userModelFirebase.getQuantidadeTotalLancamentosSaidaMensal());
                this.userModelJason.setQuantidadeTotalLancamentosEntradaMensal(userModelFirebase.getQuantidadeTotalLancamentosEntradaMensal());

            }else{
                UserModel semDados = new UserModel();
                semDados.setValorTotalSaidaMensal(0);
                semDados.setValorTotalEntradaMensal(0);
                semDados.setQuantidadeTotalLancamentosSaidaMensal(0);
                semDados.setQuantidadeTotalLancamentosEntradaMensal(0);


                this.userModelJason.setValorTotalSaidaMensal(semDados.getValorTotalSaidaMensal());
                this.userModelJason.setValorTotalEntradaMensal(semDados.getValorTotalEntradaMensal());
                this.userModelJason.setQuantidadeTotalLancamentosSaidaMensal(semDados.getQuantidadeTotalLancamentosSaidaMensal());
                this.userModelJason.setQuantidadeTotalLancamentosEntradaMensal(semDados.getQuantidadeTotalLancamentosEntradaMensal());

            }

        }
        return this.userModelJason;
    }
    //RECUPERAR VALORES DO MÊS DE ABRIL  FIM METODO

    //RECUPERAR VALORES DO MÊS DE MAIO USUARIO INICIO METODO
    public UserModel getAcumuladoReferenciaMaio(UserModel userModel) throws ExecutionException, InterruptedException {


        this.emailCliente = userModel.getEmailUser();
        this.nomeUser = userModel.getNomeUser();


        String nomeMaiusculo = this.nomeUser.toUpperCase();
        String nomeCollectionAcumuladoUsuario = "ACUMULADO_"+nomeMaiusculo;

        if(this.emailCliente != null){

            Firestore firebaseDadosJaneiro = FirestoreClient.getFirestore();
            DocumentReference collectionReferenceSugestao = firebaseDadosJaneiro.collection(NOME_COLLECTION_ACUMULADOS).document(this.emailCliente)
                    .collection(nomeCollectionAcumuladoUsuario)
                    .document("ACUMULADO_MES_MAIO");


            ApiFuture<DocumentSnapshot> dadosJaneiro = collectionReferenceSugestao.get();
            DocumentSnapshot documentSnapshotDadosJaneiro = dadosJaneiro.get();
            if(documentSnapshotDadosJaneiro.exists()){

                UserModel userModelFirebase = documentSnapshotDadosJaneiro.toObject(UserModel.class);
                this.userModelJason.setNomeUser(userModelFirebase.getNomeUser());
                this.userModelJason.setEmailUser(userModelFirebase.getEmailUser());
                this.userModelJason.setValorTotalSaidaMensal(userModelFirebase.getValorTotalSaidaMensal());
                this.userModelJason.setValorTotalEntradaMensal(userModelFirebase.getValorTotalEntradaMensal());
                this.userModelJason.setQuantidadeTotalLancamentosSaidaMensal(userModelFirebase.getQuantidadeTotalLancamentosSaidaMensal());
                this.userModelJason.setQuantidadeTotalLancamentosEntradaMensal(userModelFirebase.getQuantidadeTotalLancamentosEntradaMensal());

            }else{
                UserModel semDados = new UserModel();
                semDados.setValorTotalSaidaMensal(0);
                semDados.setValorTotalEntradaMensal(0);
                semDados.setQuantidadeTotalLancamentosSaidaMensal(0);
                semDados.setQuantidadeTotalLancamentosEntradaMensal(0);


                this.userModelJason.setValorTotalSaidaMensal(semDados.getValorTotalSaidaMensal());
                this.userModelJason.setValorTotalEntradaMensal(semDados.getValorTotalEntradaMensal());
                this.userModelJason.setQuantidadeTotalLancamentosSaidaMensal(semDados.getQuantidadeTotalLancamentosSaidaMensal());
                this.userModelJason.setQuantidadeTotalLancamentosEntradaMensal(semDados.getQuantidadeTotalLancamentosEntradaMensal());

            }

        }
        return this.userModelJason;
    }
    //RECUPERAR VALORES DO MÊS DE MAIO  FIM METODO

    //RECUPERAR VALORES DO MÊS DE JUNHO USUARIO INICIO METODO
    public UserModel getAcumuladoReferenciaJunho(UserModel userModel) throws ExecutionException, InterruptedException {


        this.emailCliente = userModel.getEmailUser();
        this.nomeUser = userModel.getNomeUser();


        String nomeMaiusculo = this.nomeUser.toUpperCase();
        String nomeCollectionAcumuladoUsuario = "ACUMULADO_"+nomeMaiusculo;

        if(this.emailCliente != null){

            Firestore firebaseDadosJaneiro = FirestoreClient.getFirestore();
            DocumentReference collectionReferenceSugestao = firebaseDadosJaneiro.collection(NOME_COLLECTION_ACUMULADOS).document(this.emailCliente)
                    .collection(nomeCollectionAcumuladoUsuario)
                    .document("ACUMULADO_MES_JUNHO");


            ApiFuture<DocumentSnapshot> dadosJaneiro = collectionReferenceSugestao.get();
            DocumentSnapshot documentSnapshotDadosJaneiro = dadosJaneiro.get();
            if(documentSnapshotDadosJaneiro.exists()){

                UserModel userModelFirebase = documentSnapshotDadosJaneiro.toObject(UserModel.class);
                this.userModelJason.setNomeUser(userModelFirebase.getNomeUser());
                this.userModelJason.setEmailUser(userModelFirebase.getEmailUser());
                this.userModelJason.setValorTotalSaidaMensal(userModelFirebase.getValorTotalSaidaMensal());
                this.userModelJason.setValorTotalEntradaMensal(userModelFirebase.getValorTotalEntradaMensal());
                this.userModelJason.setQuantidadeTotalLancamentosSaidaMensal(userModelFirebase.getQuantidadeTotalLancamentosSaidaMensal());
                this.userModelJason.setQuantidadeTotalLancamentosEntradaMensal(userModelFirebase.getQuantidadeTotalLancamentosEntradaMensal());

            }else{

               /* UserModel semDados = new UserModel();
                semDados.setValorTotalSaidaMensal(0);
                semDados.setValorTotalEntradaMensal(0);
                semDados.setQuantidadeTotalLancamentosSaidaMensal(0);
                semDados.setQuantidadeTotalLancamentosEntradaMensal(0);


                this.userModelJason.setValorTotalSaidaMensal(semDados.getValorTotalSaidaMensal());
                this.userModelJason.setValorTotalEntradaMensal(semDados.getValorTotalEntradaMensal());
                this.userModelJason.setQuantidadeTotalLancamentosSaidaMensal(semDados.getQuantidadeTotalLancamentosSaidaMensal());
                this.userModelJason.setQuantidadeTotalLancamentosEntradaMensal(semDados.getQuantidadeTotalLancamentosEntradaMensal());*/

                this.userModelJason = null;

            }

        }
        return this.userModelJason;
    }
    //RECUPERAR VALORES DO MÊS DE JUNHO  FIM METODO

    //RECUPERAR VALORES DO MÊS DE JULHO  INICIO METODO
    public UserModel getAcumuladoReferenciaJulho(UserModel userModel) throws ExecutionException, InterruptedException {


        this.emailCliente = userModel.getEmailUser();
        this.nomeUser = userModel.getNomeUser();


        String nomeMaiusculo = this.nomeUser.toUpperCase();
        String nomeCollectionAcumuladoUsuario = "ACUMULADO_"+nomeMaiusculo;

        if(this.emailCliente != null){

            Firestore firebaseDadosJaneiro = FirestoreClient.getFirestore();
            DocumentReference collectionReferenceSugestao = firebaseDadosJaneiro.collection(NOME_COLLECTION_ACUMULADOS).document(this.emailCliente)
                    .collection(nomeCollectionAcumuladoUsuario)
                    .document("ACUMULADO_MES_JULHO");


            ApiFuture<DocumentSnapshot> dadosJaneiro = collectionReferenceSugestao.get();
            DocumentSnapshot documentSnapshotDadosJaneiro = dadosJaneiro.get();
            if(documentSnapshotDadosJaneiro.exists()){

                UserModel userModelFirebase = documentSnapshotDadosJaneiro.toObject(UserModel.class);
                this.userModelJason.setNomeUser(userModelFirebase.getNomeUser());
                this.userModelJason.setEmailUser(userModelFirebase.getEmailUser());
                this.userModelJason.setValorTotalSaidaMensal(userModelFirebase.getValorTotalSaidaMensal());
                this.userModelJason.setValorTotalEntradaMensal(userModelFirebase.getValorTotalEntradaMensal());
                this.userModelJason.setQuantidadeTotalLancamentosSaidaMensal(userModelFirebase.getQuantidadeTotalLancamentosSaidaMensal());
                this.userModelJason.setQuantidadeTotalLancamentosEntradaMensal(userModelFirebase.getQuantidadeTotalLancamentosEntradaMensal());

            }else{
               /* UserModel semDados = new UserModel();
                semDados.setValorTotalSaidaMensal(0);
                semDados.setValorTotalEntradaMensal(0);
                semDados.setQuantidadeTotalLancamentosSaidaMensal(0);
                semDados.setQuantidadeTotalLancamentosEntradaMensal(0);


                this.userModelJason.setValorTotalSaidaMensal(semDados.getValorTotalSaidaMensal());
                this.userModelJason.setValorTotalEntradaMensal(semDados.getValorTotalEntradaMensal());
                this.userModelJason.setQuantidadeTotalLancamentosSaidaMensal(semDados.getQuantidadeTotalLancamentosSaidaMensal());
                this.userModelJason.setQuantidadeTotalLancamentosEntradaMensal(semDados.getQuantidadeTotalLancamentosEntradaMensal());*/

                this.userModelJason = null;

            }

        }
        return this.userModelJason;
    }
    //RECUPERAR VALORES DO MÊS DE JULHO  FIM METODO

    //RECUPERAR VALORES DO MÊS DE AGOSTO  INICIO METODO
    public UserModel getAcumuladoReferenciaAgosto(UserModel userModel) throws ExecutionException, InterruptedException {


        this.emailCliente = userModel.getEmailUser();
        this.nomeUser = userModel.getNomeUser();


        String nomeMaiusculo = this.nomeUser.toUpperCase();
        String nomeCollectionAcumuladoUsuario = "ACUMULADO_"+nomeMaiusculo;

        if(this.emailCliente != null){

            Firestore firebaseDadosJaneiro = FirestoreClient.getFirestore();
            DocumentReference collectionReferenceSugestao = firebaseDadosJaneiro.collection(NOME_COLLECTION_ACUMULADOS).document(this.emailCliente)
                    .collection(nomeCollectionAcumuladoUsuario)
                    .document("ACUMULADO_MES_AGOSTO");


            ApiFuture<DocumentSnapshot> dadosJaneiro = collectionReferenceSugestao.get();
            DocumentSnapshot documentSnapshotDadosJaneiro = dadosJaneiro.get();
            if(documentSnapshotDadosJaneiro.exists()){

                UserModel userModelFirebase = documentSnapshotDadosJaneiro.toObject(UserModel.class);
                this.userModelJason.setNomeUser(userModelFirebase.getNomeUser());
                this.userModelJason.setEmailUser(userModelFirebase.getEmailUser());
                this.userModelJason.setValorTotalSaidaMensal(userModelFirebase.getValorTotalSaidaMensal());
                this.userModelJason.setValorTotalEntradaMensal(userModelFirebase.getValorTotalEntradaMensal());
                this.userModelJason.setQuantidadeTotalLancamentosSaidaMensal(userModelFirebase.getQuantidadeTotalLancamentosSaidaMensal());
                this.userModelJason.setQuantidadeTotalLancamentosEntradaMensal(userModelFirebase.getQuantidadeTotalLancamentosEntradaMensal());

            }else{
                UserModel semDados = new UserModel();
                semDados.setValorTotalSaidaMensal(0);
                semDados.setValorTotalEntradaMensal(0);
                semDados.setQuantidadeTotalLancamentosSaidaMensal(0);
                semDados.setQuantidadeTotalLancamentosEntradaMensal(0);


                this.userModelJason.setValorTotalSaidaMensal(semDados.getValorTotalSaidaMensal());
                this.userModelJason.setValorTotalEntradaMensal(semDados.getValorTotalEntradaMensal());
                this.userModelJason.setQuantidadeTotalLancamentosSaidaMensal(semDados.getQuantidadeTotalLancamentosSaidaMensal());
                this.userModelJason.setQuantidadeTotalLancamentosEntradaMensal(semDados.getQuantidadeTotalLancamentosEntradaMensal());

            }

        }
        return this.userModelJason;
    }
    //RECUPERAR VALORES DO MÊS DE AGOSTO  FIM METODO

    //RECUPERAR VALORES DO MÊS DE SETEMBRO  INICIO METODO
    public UserModel getAcumuladoReferenciaSetembro(UserModel userModel) throws ExecutionException, InterruptedException {


        this.emailCliente = userModel.getEmailUser();
        this.nomeUser = userModel.getNomeUser();


        String nomeMaiusculo = this.nomeUser.toUpperCase();
        String nomeCollectionAcumuladoUsuario = "ACUMULADO_"+nomeMaiusculo;

        if(this.emailCliente != null){

            Firestore firebaseDadosJaneiro = FirestoreClient.getFirestore();
            DocumentReference collectionReferenceSugestao = firebaseDadosJaneiro.collection(NOME_COLLECTION_ACUMULADOS).document(this.emailCliente)
                    .collection(nomeCollectionAcumuladoUsuario)
                    .document("ACUMULADO_MES_SETEMBRO");


            ApiFuture<DocumentSnapshot> dadosJaneiro = collectionReferenceSugestao.get();
            DocumentSnapshot documentSnapshotDadosJaneiro = dadosJaneiro.get();
            if(documentSnapshotDadosJaneiro.exists()){

                UserModel userModelFirebase = documentSnapshotDadosJaneiro.toObject(UserModel.class);
                this.userModelJason.setNomeUser(userModelFirebase.getNomeUser());
                this.userModelJason.setEmailUser(userModelFirebase.getEmailUser());
                this.userModelJason.setValorTotalSaidaMensal(userModelFirebase.getValorTotalSaidaMensal());
                this.userModelJason.setValorTotalEntradaMensal(userModelFirebase.getValorTotalEntradaMensal());
                this.userModelJason.setQuantidadeTotalLancamentosSaidaMensal(userModelFirebase.getQuantidadeTotalLancamentosSaidaMensal());
                this.userModelJason.setQuantidadeTotalLancamentosEntradaMensal(userModelFirebase.getQuantidadeTotalLancamentosEntradaMensal());

            }else{
                UserModel semDados = new UserModel();
                semDados.setValorTotalSaidaMensal(0);
                semDados.setValorTotalEntradaMensal(0);
                semDados.setQuantidadeTotalLancamentosSaidaMensal(0);
                semDados.setQuantidadeTotalLancamentosEntradaMensal(0);


                this.userModelJason.setValorTotalSaidaMensal(semDados.getValorTotalSaidaMensal());
                this.userModelJason.setValorTotalEntradaMensal(semDados.getValorTotalEntradaMensal());
                this.userModelJason.setQuantidadeTotalLancamentosSaidaMensal(semDados.getQuantidadeTotalLancamentosSaidaMensal());
                this.userModelJason.setQuantidadeTotalLancamentosEntradaMensal(semDados.getQuantidadeTotalLancamentosEntradaMensal());
            }

        }else{
           return null;

        }
        return this.userModelJason;
    }
    //RECUPERAR VALORES DO MÊS DE SETEMBRO  FIM METODO

    //RECUPERAR LISTA DE CONTA DE SAIDA INICIO METODO
    public  String[] getListaContasSaidasCadastradas() throws ExecutionException, InterruptedException {

        String[] resultadoListaContasSaidasCadastradas = {};

        Firestore firestore = FirestoreClient.getFirestore();
        CollectionReference collectionReference = firestore.collection(NOME_COLLECTION_CONTAS_CONTAS_SAIDA);

        ApiFuture<QuerySnapshot> query = collectionReference.get();
        List<QueryDocumentSnapshot> documentSnapshots = query.get().getDocuments();
        for(QueryDocumentSnapshot doc : documentSnapshots){
            ContaSaidaModel contaSaidaModelCadastradaBanco = doc.toObject(ContaSaidaModel.class);
            resultadoListaContasSaidasCadastradas = new String[]{contaSaidaModelCadastradaBanco.getServico()};
        }
        return resultadoListaContasSaidasCadastradas;
    }
    //RECUPERAR LISTA DE CONTA DE SAIDA FIM METODO

}