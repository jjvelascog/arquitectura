import java.io.*;
import java.util.*;

public class Principal {

	/**
	 * @param args
	 */
	static int[] mem;
	static int A, B;
	static int[] var;
	static Map<String, Integer> map;
	static final String bin = "25'b00000";
	static final String end = "00000000";

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Start();
		PreLeer("texto.txt");
		Transformar("texto.txt");
	}

	public static void Start(){
		mem = new int[256];
		for(int i = 0; i<256; i++){
			mem[i] = 0;
		}
		var = new int[50];
		for(int i = 0; i< 50; i++){
			var[i] = 0;
		}
		map = new HashMap<String, Integer>();
		A=0;
		B=0;
	}

	public static void Transformar(String path){
		try{
			// Abrimos el archivo
			FileInputStream fstream = new FileInputStream(path);
			// Creamos el objeto de entrada
			DataInputStream entrada = new DataInputStream(fstream);
			// Creamos el Buffer de Lectura
			BufferedReader buffer = new BufferedReader(new InputStreamReader(entrada));
			String strLinea;
			// Leer el archivo linea por linea
			boolean data = true;
			int contador = 0;
			int linea = 0;
			while ((strLinea = buffer.readLine()) != null)   {
				// Imprimimos la l’nea por pantalla
				if(data){
					if(!strLinea.equals("")){
						String[] auxiliar = strLinea.split(" ");
						if(auxiliar[0].equals("CODE:")){
							data = false;
						}
						else if(!auxiliar[0].equals("DATA:")){
							String[] aux = strLinea.split(" ");
							var[contador] = Integer.parseInt(aux[1]);
							contador++;
						}
					}
				}
				else{
					String[] aux = strLinea.split("//");
					String nuevo = aux[0];
					String[] aux1 = nuevo.split(" ");
					String instr = nuevo.substring(0,3);
					if(!instr.equals("MOV") && !instr.equals("INC") && !instr.equals("ADD") && !instr.equals("SUB") && !instr.equals("JEQ") && !instr.equals("JMP") && !instr.equals("CMP"))
					{
						map.put(aux1[0], linea);
					}
					if(nuevo != ""){
						System.out.println(nuevo);
					}
				}
				linea++;
			}
			// Cerramos el archivo
			entrada.close();
		}catch (Exception e){ //Catch de excepciones
			System.err.println("Ocurrio un error: " + e.getMessage());
		}
	}

	public static void PreLeer(String path){
		try{
			// Abrimos el archivo
			FileInputStream fstream = new FileInputStream(path);
			// Creamos el objeto de entrada
			DataInputStream entrada = new DataInputStream(fstream);
			// Creamos el Buffer de Lectura
			BufferedReader buffer = new BufferedReader(new InputStreamReader(entrada));
			String strLinea;
			// Leer el archivo linea por linea
			int linea = 0;
			boolean data = true;
			while ((strLinea = buffer.readLine()) != null)   {
				// Imprimimos la l’nea por pantalla
				if(data){
					if(!strLinea.equals("")){
						String[] auxiliar = strLinea.split(" ");
						if(auxiliar[0].equals("CODE:")){
							data = false;
						}
					}
				}
				else{
					if(!strLinea.equals(""))
					{
						String[] aux1 = strLinea.split(" ");
						String instr = aux1[0];
						if(!instr.equals("CODE:") && !instr.equals("DATA:") && !instr.equals("MOV") && !instr.equals("INC") && !instr.equals("ADD") && !instr.equals("SUB") && !instr.equals("JEQ") && !instr.equals("JMP") && !instr.equals("CMP"))
						{
							map.put(aux1[0], linea);
						}
					}
				}
				linea++;
			}
			// Cerramos el archivo
			entrada.close();
		}catch (Exception e){ //Catch de excepciones
			System.err.println("Ocurrio un error: " + e.getMessage());
		}
	}
	
	public static String Completar(int i){	
		String a = Integer.toBinaryString(i);	
		while(a.length() < 8){
			a = 0 + a;
		}
		return a;
	}

	//Instrucciones
	
	public static String MovAB(){
		A = B;
		return bin+"000010101010"+end;
	}

	public static String MovBA(){
		B = A;
		return bin+"000001001110"+end;
	}
	
	public static String MovALit(int lit){
		A = lit;
		return bin+"000010110010"+Completar(lit);
	}

	public static String MovBLit(int lit){
		B = lit;
		return bin+"000001110010"+Completar(lit);
	}

	public static String MovADir(int dir){
		A = mem[dir];
		return bin+"000000101010"+Completar(dir);
	}

	public static String MovBDir(int dir){
		B = mem[dir];
		return bin+"000000110110"+Completar(dir);
	}

	public static String MovDirA(int dir){
		mem[dir] = A;
		return bin+"000000001111"+Completar(dir);
	}

	public static String MovDirB(int dir){
		mem[dir] = B;
		return bin+"000000101011"+Completar(dir);
	}

	public static String MovADirB(){
		A = mem[B];
		return bin+"100010100110"+end;
	}
	
	public static String MovBDirA(){
		B = mem[A];
		return bin+"100001100110"+end;
	}
	
	public static String MovDirBA(){
		mem[B] = A;
		return bin+"100000001111"+end;
	}
	
	public static String AddAB(){
		A += B;
		return bin+"000010001010"+end;
	}

	public static String AddBA(){
		B += A;
		return bin+"000001001010"+end;
	}

	public static String AddALit(int lit){
		A += lit;
		return bin+"000010000010"+Completar(lit);
	}
	
	public static String AddBLit(int lit){
		B += lit;
		return bin+"000001000010"+Completar(lit);
	}
	
	public static String AddADir(int dir){
		A += mem[dir];
		return bin+"000010000110"+Completar(dir);
	}

	public static String AddADirB(){
		A += mem[B];
		return bin+"100010000110"+end;
	}

	public static String AddDir(int dir){
		mem[dir] = A + B;
		return bin+"000000001011"+Completar(dir);
	}
	
	public static String SubAB(){
		A -= B;
		return bin+"000010001000"+end;
	}

	public static String SubBA(){
		B -= A;
		return bin+"000001001000"+end;
	}

	public static String SubALit(int lit){
		A -= lit;
		return bin+"000010000000"+Completar(lit);
	}
	
	public static String SubBLit(int lit){
		B -= lit;
		return bin+"000001000000"+Completar(lit);
	}	
	
	public static String SubADir(int dir){
		A -= mem[dir];
		return bin+"000010000100"+Completar(dir);
	}

	public static String SubADirB(){
		A -= mem[B];
		return bin+"100010000100"+end;
	}
	
	public static String SubDir(int dir){
		mem[dir] = A - B;
		return bin+"000000001001"+Completar(dir);
	}
	
	public static String IncA(){
		A++;
		return bin+"000010000010"+"00000001";
	}
	
	public static String IncB(){
		B++;
		return bin+"000001011010"+end;
	}

	public static String DecA(){
		A--;
		return bin+"0000100000"+"00000001";
	}
	
	public static String JEQ(int inst){
		return bin+"001000000000"+Completar(inst);
	}

	public static String JNE(int inst){
		return bin+"010000000000"+Completar(inst);
	}
	
	public static String JMP(int inst){
		return bin+"000100000000"+Completar(inst);
	}

}
