import java.util.ArrayList;
import java.util.Arrays;

public class AlgoritmoCYK {

	public String cadena; 
	public ArrayList<ArrayList<String>> Gramatica;
	public ArrayList<Nodo>[][] MatrizCYK;
	
	public AlgoritmoCYK(ArrayList<ArrayList<String>> Gramatica, String cadena) {
		this.cadena = cadena;
		this.Gramatica = Gramatica;
		
		this.MatrizCYK = new ArrayList[cadena.length()][cadena.length()];
		
		for(int i=0; i<MatrizCYK.length;i++) {
			for(int j=0; j<MatrizCYK.length;j++) {
				MatrizCYK[i][j] = new ArrayList<Nodo>();
			}
		}
	}
	
	public boolean resolverCYK() {
		
		for(int i = 0; i<MatrizCYK.length;i++) {
			for(ArrayList<String> Produccion : Gramatica) {
				if(Produccion.contains(Character.toString(cadena.charAt(i)))){
					MatrizCYK[i][i].add(new Nodo(Produccion.get(0)));
				}
			}
		}
		
		/*
		int cont = 1;
		for(int i=0;i<MatrizCYK.length-1;i++) {
			for(int j = 0;j<MatrizCYK.length-1;j++) {
				System.out.println(i+""+(i+cont));
				MatrizCYK[i][i+cont]=resolverCasilla(i,i+cont);
			}
		}
		*/
	
		for(int cont = 1; cont<MatrizCYK.length;cont++) {
			for(int i=0;i<MatrizCYK.length-cont;i++) {
				MatrizCYK[i][i+cont]=resolverCasilla(i,i+cont);
			}
		}
		
		for(Nodo node : MatrizCYK[0][MatrizCYK.length-1]) {
			if(node.raiz.equals("S")){
				return true; 
			}
		}
		
		return false; 
	}
	
	public void imprimirMatriz() {
		for(int i = 0; i<this.MatrizCYK.length;i++) {
			for(int j = 0; j<this.MatrizCYK.length;j++) {
				for(int k=0; k<this.MatrizCYK[i][j].size();k++) {
					System.out.print(MatrizCYK[i][j].get(k).raiz + " ");
				}
			}
			System.out.println();
		}
	}
	
	public ArrayList<Nodo> resolverCasilla(int i, int j){
		//i = numero de fila
		//j = numero de columna
		int columna = i;
		ArrayList<Nodo> resultado = new ArrayList<Nodo>();
		for(int fila= i+1; fila<MatrizCYK.length;fila++) {
			//System.out.println(fila+""+columna);
			//columna ira disminuyendo para representar los cuadros a la izquierda, y fila aumentado para representar los cuadros abajo
			for(int x = 0; x<MatrizCYK[fila][j].size();x++) {
				for(int y = 0; y<MatrizCYK[i][columna].size();y++) {
					resultado.addAll(checar(MatrizCYK[i][columna].get(y),MatrizCYK[fila][j].get(x)));
				}
			}
			columna++;
		}
		return resultado;
	}
	
	public ArrayList<Nodo> checar(Nodo a, Nodo b) {
		ArrayList<Nodo> resultado = new ArrayList<Nodo>();
		String cadenaAChecar = a.raiz + b.raiz;
		for(int i=0; i<Gramatica.size();i++) {
			for(int j= 1; j < Gramatica.get(i).size(); j++) {
				if(Gramatica.get(i).get(j).equals(cadenaAChecar)) {
					resultado.add(new Nodo(Gramatica.get(i).get(0),a,b));
				}
			}
		}
		return resultado; 
	}
	
	public void imprimirDerivacion() {
		for(Nodo node : MatrizCYK[0][MatrizCYK.length-1]) {
			if(node.raiz.equals("S")){
				imprimirNodo(node);
				break;
			}
		}
	}
	
	public void imprimirNodo(Nodo node) {
		if(node.tieneHijos()) {
			System.out.println(node.raiz);
			imprimirNodo(node.hijoDerecho);
			imprimirNodo(node.hijoIzquierdo);
		}else {
			System.out.println(node.raiz);
		}
	}
	
	public static void main(String[] args) {
		ArrayList<String> S = new ArrayList<String>(Arrays.asList("S","AB","SS","AC","BD","BA"));
		ArrayList<String> A = new ArrayList<String>(Arrays.asList("A","a"));
		ArrayList<String> B = new ArrayList<String>(Arrays.asList("B","b"));
		ArrayList<String> C = new ArrayList<String>(Arrays.asList("C","SB"));
		ArrayList<String> D = new ArrayList<String>(Arrays.asList("D","SA"));
		
		ArrayList<ArrayList<String>> Gramatica = new ArrayList<ArrayList<String>>(Arrays.asList(S,A,B,C,D));
		
		String cadena = "aabbab";
		
		AlgoritmoCYK test = new AlgoritmoCYK(Gramatica, cadena);
		Boolean pr = test.resolverCYK();
		test.imprimirMatriz();
		System.out.println(pr);
		
		System.out.println();
		test.imprimirDerivacion();
	}
	
}

class Nodo{
	
	public String raiz;
	public Nodo hijoDerecho;
	public Nodo hijoIzquierdo;
	
	public Nodo(String raiz) {
		this.raiz = raiz; 
	}
	
	public Nodo(String raiz, Nodo hijoDerecho, Nodo hijoIzquierdo) {
		this.raiz = raiz;
		this.hijoDerecho = hijoDerecho;
		this.hijoIzquierdo = hijoIzquierdo; 
	}
	
	public boolean tieneHijos() {
		return hijoDerecho != null && hijoIzquierdo != null; 
	}
}
