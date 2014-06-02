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

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		start();
		PreLeer("texto.txt");
		Transformar("texto.txt");
	}

	public static void start(){
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

	public static String MovAB(){
		A = B;
		return "25Õb0000000001010101000000000";
	}

	public static String MovBA(){
		B = A;
		return "25Õb0000000 000100111000000000";
	}

	public static String MovALit(int lit){
		A = lit;
		String a = Integer.toBinaryString(lit);
		return "25Õb0000000 0010110010"+a;
	}

	public static String MovBLit(int lit){
		B = lit;
		String a = Integer.toBinaryString(lit);
		return "25Õb0000000 0001110010"+a;
	}

	public static String MovADir(int dir){
		A = mem[dir];
		String a = Integer.toBinaryString(dir);
		return "25Õb0000000 0000101010"+a;
	}

	public static String MovBDir(int dir){
		B = mem[dir];
		String a = Integer.toBinaryString(dir);
		return "25Õb0000000 0000110110"+a;
	}

	public static String MovDirA(int dir){
		mem[dir] = A;
		String a = Integer.toBinaryString(dir);
		return "25Õb0000000 0000001111"+a;
	}

	public static String MovDirB(int dir){
		mem[dir] = B;
		String a = Integer.toBinaryString(dir);
		return "25Õb0000000 0000101011"+a;
	}

	public static String AddAB(){
		A += B;
		return "25Õb0000000 001000101000000000";
	}

	public static String AddBA(){
		B += A;
		return "25Õb0000000 000100101000000000";
	}

	public static String AddALit(int dir){
		A += mem[dir];
		String a = Integer.toBinaryString(dir);
		return "25Õb0000000 0010000010"+a;
	}

	/*
	public static String AddBLit(int dir){
		mem[dir] = B;
		String a = Integer.toBinaryString(dir);
		return "25Õb0000000 0000101011"+a;
	}
	 */

	public static String SubAB(){
		A -= B;
		return "25Õb0000000 001000100000000000";
	}

	/*
	public static String SubBA(){
		B -= A;
		return "25Õb0000000 0010000010";
	}
	 */

	public static String SubALit(int dir){
		A -= mem[dir];
		String a = Integer.toBinaryString(dir);
		return "25Õb0000000 0010000000"+a;
	}

	/*
	public static String SubBLit(int dir){
		B += mem[dir];
		String a = Integer.toBinaryString(dir);
		return "25Õb0000000 0010000010"+a;
	}
	 */

	public static String IncB(){
		B++;
		return "25Õb0000000 000101101000000000";
	}

	public static String JEQ(int inst){
		String a = Integer.toBinaryString(inst);
		return "25Õb0000000 1000000000 00000000";
	}

	public static String JMP(int inst){
		String a = Integer.toBinaryString(inst);
		return "25Õb0000000 0100000000 00000000";
	}

	public static String CMPAB(){
		return "25Õb0000000 000000100000000000";
	}

	public static String CMPALit(int lit){
		String a = Integer.toBinaryString(lit);
		return "25Õb0000000 0000000000 xxxxxxxx";
	}


}
