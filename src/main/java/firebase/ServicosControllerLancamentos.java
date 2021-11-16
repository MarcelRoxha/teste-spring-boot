package firebase;


import model.*;
import model.ClienteModel;
import model.ContaEntradaLancamentosFuturosModel;
import model.ContaEntradaModel;
import model.ContaSaidaModel;
import model.LancamentoEntradaModel;
import model.LancamentoSaidaModel;
import model.UserModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;


@RestController
@RequestMapping("/")
@CrossOrigin("*")
public class ServicosControllerLancamentos {


    @Autowired
    public ServicosService servicosService;

    public ServicosControllerLancamentos(ServicosService servicosService){
        this.servicosService = servicosService;
    }


    //--------------------LANÇAMENTO ENTRADA INICIO-------------------------------//

    //Salvar lancamento entrada-----------------------------

    @PostMapping("/lancarEntrada")
    public LancamentoEntradaModel lancarEntrada(@RequestBody LancamentoEntradaModel lancamentoEntradaModel) throws ExecutionException, InterruptedException {
        return servicosService.lancarEntrada(lancamentoEntradaModel);
    }

    @PostMapping("/salvarSugestao")
    public ContaEntradaLancamentosFuturosModel salvarSugestao(@RequestBody ContaEntradaLancamentosFuturosModel ContaEntradaLancamentosFuturosModel) throws ExecutionException, InterruptedException {
        return servicosService.salvarLancamentosFuturos(ContaEntradaLancamentosFuturosModel);
    }

    //Editar lancamento entrada-----------------------------

    @PutMapping("/editarEntrada")
    public String editarEntrada(@RequestBody LancamentoEntradaModel lancamentoEntradaModel) {
        return servicosService.editarLancamentoEntrada(lancamentoEntradaModel);
    }


    @PostMapping("/lancarSaida")
    public LancamentoSaidaModel lancarSaida(@RequestBody LancamentoSaidaModel lancamentoSaidaModel) throws ExecutionException, InterruptedException {
        return servicosService.lancarSaida(lancamentoSaidaModel);
    }

    //Editar lancamento saida
    @PutMapping("/editarSaida")
    public String editarSaida(@RequestBody LancamentoEntradaModel lancamentoEntradaModel) {
        return servicosService.editarLancamentoSaida(lancamentoEntradaModel);
    }

    //Deletar lancamento saida
  /*  @GetMapping("/deletarLanceSaida")
    public String deletarLanceSaida(@RequestParam String collection) {
        return servicosService.deletarSaidaLancada(collection);
    }
*/
    //--------------------LANÇAMENTO SAIDA FIM-------------------------------//



    //--------------------LISTA LANÇAMENTOS INICIO-------------------------------//


    //Lista entradas---------------------------
    @GetMapping("/listaEntradasLancadas")
    public List<LancamentoEntradaModel> listaEntradasLancadas() throws ExecutionException, InterruptedException, IOException {
        return servicosService.getLancarEntrada();
    }

    @GetMapping("/recuperarClientes")
    public List<ClienteModel> listaClientesCadastrados() throws ExecutionException, InterruptedException {
        return servicosService.getListaClientesCadastrados();
    }

    //Cadastrar Conta de Saida
    @PostMapping("/cadastrarContaEntrada")
    public ContaEntradaModel contaEntradaModel(@RequestBody ContaEntradaModel contaEntradaModel) throws ExecutionException, InterruptedException {
        return servicosService.cadastrarContaEntrada(contaEntradaModel);
    }

    @GetMapping("/recuperarContasEntrada")
    public List<ContaEntradaModel> listaContasEntradaCadastradas() throws ExecutionException, InterruptedException {
        return servicosService.getListaContasEntradaCadastradas();
    }

    @GetMapping("/recuperarContasSaida")
    public String[] listaContasSaidaCadastradas() throws ExecutionException, InterruptedException {

        return servicosService.getListaContasSaidasCadastradas();
    }

    //Cadastrar Conta de entrada-----------------------------

    @PostMapping("/cadastrarContaSaida")
    public ContaSaidaModel contaSaidaModel(@RequestBody ContaSaidaModel contaSaidaModel) throws ExecutionException, InterruptedException {
        return servicosService.cadastrarContaSaida(contaSaidaModel);
    }

    //Deletar lancamento entrada----------------------------

  /*  @GetMapping("/deletarLanceEntrada")
    public String deletarLanceEntrada(@RequestParam String collection) {
        return servicosService.deletarEntradaLancada(collection);
    }*/

    //--------------------LANÇAMENTO ENTRADA FIM-------------------------------//

    @GetMapping("/teste")
    public ResponseEntity<String> testGetEndpoint(){
        return ResponseEntity.ok("<h1>Teste get Endapoint is Working</h1>");
    }


    //CLIENTES
    @PostMapping("/cadastrarCliente")
    public ClienteModel cadastrarCliente(@RequestBody ClienteModel clienteModel) throws ExecutionException, InterruptedException {
        return servicosService.cadastrarCliente(clienteModel);
    }
    @PostMapping("/editarCliente")
    public String editarCliente(@RequestBody ClienteModel clienteModel) throws ExecutionException, InterruptedException {
        return servicosService.editarCliente(clienteModel);
    }



    @PostMapping("/recuperarInformacoesCliente")
    public ClienteModel informacoesUsuario(@RequestBody String idPassado) throws ExecutionException, InterruptedException {
        return servicosService.recuperarInformacoesCliente(idPassado);
    }


    @PostMapping("/acumulado-janeiro")
    public UserModel dadosJaneiroUsuario(@RequestBody UserModel userModel) throws ExecutionException, InterruptedException {
        return servicosService.recuperarValoresUsuarioJaneiro(userModel);
    }

    @PostMapping("/acumulado-fevereiro")
    public UserModel acumuladoFevereiro(@RequestBody UserModel userModel) throws ExecutionException, InterruptedException {
        return servicosService.getAcumuladoReferenciaFevereiro(userModel);
    }

    @PostMapping("/acumulado-marco")
    public UserModel acumuladoMarco(@RequestBody UserModel userModel) throws ExecutionException, InterruptedException {
        return servicosService.getAcumuladoReferenciaMarco(userModel);
    }

    @PostMapping("/acumulado-abril")
    public UserModel acumuladoAbril(@RequestBody UserModel userModel) throws ExecutionException, InterruptedException {
        return servicosService.getAcumuladoReferenciaAbril(userModel);
    }

    @PostMapping("/acumulado-maio")
    public UserModel acumuladoMaio(@RequestBody UserModel userModel) throws ExecutionException, InterruptedException {
        return servicosService.getAcumuladoReferenciaMaio(userModel);
    }

    @PostMapping("/acumulado-junho")
    public UserModel acumuladoJunho(@RequestBody UserModel userModel) throws ExecutionException, InterruptedException {
        return servicosService.getAcumuladoReferenciaJunho(userModel);
    }

    @PostMapping("/acumulado-julho")
    public UserModel acumuladoJulho(@RequestBody UserModel userModel) throws ExecutionException, InterruptedException {
        return servicosService.getAcumuladoReferenciaJulho(userModel);
    }

    @PostMapping("/acumulado-agosto")
    public UserModel acumuladoAgosto(@RequestBody UserModel userModel) throws ExecutionException, InterruptedException {
        return servicosService.getAcumuladoReferenciaAgosto(userModel);
    }

    @PostMapping("/acumulado-setembro")
    public UserModel acumuladoSetembro(@RequestBody UserModel userModel) throws ExecutionException, InterruptedException {
        return servicosService.getAcumuladoReferenciaSetembro(userModel);
    }

    @PostMapping("/acumulado-outubro")
    public UserModel acumuladoOutubro(@RequestBody UserModel userModel) throws ExecutionException, InterruptedException {
        return servicosService.getAcumuladoReferenciaOutubro(userModel);
    }

    @PostMapping("/acumulado-novembro")
    public UserModel acumuladoNovembro(@RequestBody UserModel userModel) throws ExecutionException, InterruptedException {
        return servicosService.getAcumuladoReferenciaNovembro(userModel);
    }

    @PostMapping("/acumulado-dezembro")
    public UserModel acumuladoDezembro(@RequestBody UserModel userModel) throws ExecutionException, InterruptedException {
        return servicosService.getAcumuladoReferenciaDezembro(userModel);
    }




}
