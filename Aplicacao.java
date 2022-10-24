import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.String;
import java.util.Scanner;
import java.util.Vector;

// fila 
class Fila {

	private Jogo[] fila;
	private int frente;
	private int tras;
	private int tamanho;
	
	public Fila(int tamanho) {
		
		fila = new Jogo[tamanho];
		frente = 0;
		tras = 0;
		this.tamanho = tamanho;
	}
	
	public boolean filaVazia() {
	
		boolean resp;
		
		if (frente == tras)
			resp = true;
		else
			resp = false;
		
		return resp;
	}
	
	public boolean filaCheia() {
	
		boolean resp;
		
		if (((tras + 1) % tamanho) == (frente % tamanho))
			resp = true;
		else
			resp = false;
		
		return resp;
	}
	
	public void enfileirar(Jogo novo) throws Exception{
		
		int posicao;
		
		if (! filaCheia()) {
			posicao = tras % tamanho;
			fila[posicao] = novo;
			tras++;
		} else
			this.desenfileirar();
            this.enfileirar(novo);
	}
	
	public Jogo desenfileirar() throws Exception{
		
		Jogo desenfileirado;
		int posicao;
		
		if (! filaVazia()) {
			posicao = frente % tamanho;
			desenfileirado = fila[posicao];
			frente++;
			return desenfileirado;
		} else
			throw new Exception("Não foi possível desenfileirar nenhum elemento: a fila está vazia!");
	}

   public void obterMediasgGols(){
       int somaGeral = 0;
       int jogosGeral = 0;
       for (int i = frente; i < tras; i++) {
		   int posicao = i % tamanho;
            int mediaIndividual = fila[posicao].placarSelecao1 + fila[posicao].placarSelecao2 / 2;
            somaGeral += mediaIndividual;
            jogosGeral++;
       }
       System.out.println(somaGeral/jogosGeral);
   }


}

// jogo 
public class Jogo {
	private int dia, mes, ano, placarSelecao1, placarSelecao2;
	private String etapa, selecao1, selecao2, local;

	public int getDia() {
		return dia;
	}

	public void setDia(int dia) {
		this.dia = dia;
	}

	public int getMes() {
		return mes;
	}

	public void setMes(int mes) {
		this.mes = mes;
	}

	public int getAno() {
		return ano;
	}

	public void setAno(int ano) {
		this.ano = ano;
	}

	public String getEtapa() {
		return etapa;
	}

	public void setEtapa(String etapa) {
		this.etapa = etapa;
	}

	public String getSelecao1() {
		return selecao1;
	}

	public void setSelecao1(String selecao1) {
		this.selecao1 = selecao1;
	}

	public String getSelecao2() {
		return selecao2;
	}

	public void setSelecao2(String selecao2) {
		this.selecao2 = selecao2;
	}

	public int getPlacarSelecao1() {
		return placarSelecao1;
	}

	public void setPlacarSelecao1(int placarSelecao1) {
		this.placarSelecao1 = placarSelecao1;
	}

	public int getPlacarSelecao2() {
		return placarSelecao2;
	}

	public void setPlacarSelecao2(int placarSelecao2) {
		this.placarSelecao2 = placarSelecao2;
	}

	public String getLocal() {
		return local;
	}

	public void setLocal(String local) {
		this.local = local;
	}

	public Jogo(int ano, String etapa, int dia, int mes, String selecao1, int placarSelecao1, int placarSelecao2,
			String selecao2, String local) {
		setAno(ano);
		setEtapa(etapa);
		setDia(dia);
		setMes(mes);
		setSelecao1(selecao1);
		setPlacarSelecao1(placarSelecao1);
		setPlacarSelecao2(placarSelecao2);
		setSelecao2(selecao2);
		setLocal(local);
	}

	public Jogo() {
		setAno(0);
		setEtapa("");
		setDia(0);
		setMes(0);
		setSelecao1("");
		setPlacarSelecao1(0);
		setPlacarSelecao2(0);
		setSelecao2("");
		setLocal("");
	}

	public Jogo clone() {
		return new Jogo(this.getAno(), this.getEtapa(), this.getDia(), this.getMes(), this.getSelecao1(),
				this.getPlacarSelecao1(), this.getPlacarSelecao2(), this.getSelecao2(), this.getLocal());
	}

	static Scanner scanner = new Scanner(System.in);
	static Vector<Jogo> vetor = new Vector<Jogo>();

	public static void ler() throws NumberFormatException, IOException {
		BufferedReader arquivo = new BufferedReader(new FileReader("/tmp/partidas.txt"));
		String input;
		while ((input = arquivo.readLine()) != null) {
			String[] array = input.split("#");
			Jogo game = new Jogo(Integer.parseInt(array[0]), array[1], Integer.parseInt(array[2]),
					Integer.parseInt(array[3]), array[4], Integer.parseInt(array[5]), Integer.parseInt(array[6]),
					array[7], array[8]);
			vetor.add(game);
		}
	}

	public static void imprimir() {
		String s = scanner.nextLine();
		String[] array = s.split("[/;]+");
		for (Jogo jogo : vetor)
			if (Integer.parseInt(array[0]) == jogo.getDia() && Integer.parseInt(array[1]) == jogo.getMes()
					&& Integer.parseInt(array[2]) == jogo.getAno()
					&& (array[3].equals(jogo.getSelecao1()) || array[3].equals(jogo.getSelecao2())))
				System.out.println("[COPA " + jogo.getAno() + "] [" + jogo.getEtapa() + "] [" + jogo.getDia() + "/"
						+ jogo.getMes() + "] [" + jogo.getSelecao1() + " (" + jogo.getPlacarSelecao1() + ") x ("
						+ jogo.getPlacarSelecao2() + ") " + jogo.getSelecao2() + "] [" + jogo.getLocal() + "]");
	}
    
}

class Aplicacao {
    Jogo[] listaJogos = new Jogo[1000];

	public static void main(String[] args) {
        Fila minhaFila = new Fila(101);
        int indexFila = 0;
        File arquivo = new File("./tmp/arquivos.txt");
        Scanner lerArquivo = new Scanner(arquivo);
        String proximo = lerArquivo.nextLine();
        do {
            listaJogos[indexFila] = new Jogo(proximo);
            minhaFila.enfileirar(listaJogos[indexFila]);
            indexFila++;
            minhaFila.obterMediasgGols();
            proximo = lerArquivo.nextLine();
        } while(! proximo.equals("FIM"));
	}
}