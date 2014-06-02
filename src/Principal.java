import java.io.*;
import java.util.*;

public class Principal {

	/**
	 * @param args
	 */

	static int[] mem;
	static int A, B;
	static Map<String, Integer> var;
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
		var = new HashMap<String, Integer>();
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
							var.put(aux[0], Integer.parseInt(aux[1]));
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
					if(!nuevo.equals("")){
						String[] info = nuevo.split(" ");
						if(info[0].equals("MOV")){
							String[] datos = info[1].split(",");
							if(datos[0].equals("A") && datos[1].equals("B")){
								System.out.println(MovAB());
							}
							if(datos[0].equals("B") && datos[1].equals("A")){
								System.out.println(MovBA());
							}
							if(datos[0].equals("A") && !datos[1].equals("B") && !datos[1].contains("(")){
								if(var.containsKey(datos[1])){
									System.out.println(MovALit(var.get(datos[1])));
								}
								else{
									System.out.println(MovALit(Integer.parseInt(datos[1])));
								}
							}
							if(datos[0].equals("B") && !datos[1].equals("A") && !datos[1].contains("(")){
								if(var.containsKey(datos[1])){
									System.out.println(MovBLit(var.get(datos[1])));
								}
								else{
									System.out.println(MovBLit(Integer.parseInt(datos[1])));
								}
							}
							if(datos[0].equals("A") && !datos[1].equals("B") && datos[1].contains("(")){
								String dir = datos[1].substring(1, datos[1].length());
								if(var.containsKey(dir)){
									System.out.println(MovADir(var.get(dir)));
								}
								else if(dir.equals("A")){
									System.out.println(MovADir(A));
								}
								else if(dir.equals("B")){
									System.out.println(MovADir(B));
								}
								else{
									System.out.println(MovADir(Integer.parseInt(dir)));
								}
							}
							if(datos[0].equals("B") && !datos[1].equals("A") && datos[1].contains("(")){
								String dir = datos[1].substring(1, datos[1].length());
								if(var.containsKey(dir)){
									System.out.println(MovBDir(var.get(dir)));
								}
								else if(dir.equals("A")){
									System.out.println(MovBDir(A));
								}
								else if(dir.equals("B")){
									System.out.println(MovBDir(B));
								}
								else{
									System.out.println(MovBDir(Integer.parseInt(dir)));
								}
							}
							if(datos[0].contains("(") && datos[1].equals("A")){
								String dir = datos[0].substring(1, datos[0].length());
								if(var.containsKey(dir)){
									System.out.println(MovDirA(var.get(dir)));
								}
								else if(dir.equals("A")){
									System.out.println(MovDirA(A));
								}
								else if(dir.equals("B")){
									System.out.println(MovDirA(B));
								}
								else{
									System.out.println(MovDirA(Integer.parseInt(dir)));
								}
							}
							if(datos[0].contains("(") && datos[1].equals("B")){
								String dir = datos[0].substring(1, datos[0].length());
								if(var.containsKey(dir)){
									System.out.println(MovDirB(var.get(dir)));
								}
								else if(dir.equals("A")){
									System.out.println(MovDirB(A));
								}
								else if(dir.equals("B")){
									System.out.println(MovDirB(B));
								}
								else{
									System.out.println(MovDirB(Integer.parseInt(dir)));
								}
							}
						}
						else if(info[0].equals("ADD")){
							String[] datos = info[1].split(",");
							if(datos.length <= 1){
								String dir = datos[0].substring(1, datos[0].length());
								System.out.println(AddDir(Integer.parseInt(dir)));
							}
							else if(datos[0].equals("A") && datos[1].equals("B")){
								System.out.println(AddAB());
							}
							else if(datos[0].equals("B") && datos[1].equals("A")){
								System.out.println(AddBA());
							}
							else if(datos[0].equals("A") && !datos[1].equals("B") && !datos[1].contains("(")){
								if(var.containsKey(datos[1])){
									System.out.println(AddALit(var.get(datos[1])));
								}
								else{
									System.out.println(AddALit(Integer.parseInt(datos[1])));
								}
							}
							else if(datos[0].equals("B") && !datos[1].equals("A") && !datos[1].contains("(")){
								if(var.containsKey(datos[1])){
									System.out.println(AddBLit(var.get(datos[1])));
								}
								else{
									System.out.println(AddBLit(Integer.parseInt(datos[1])));
								}
							}
							if(datos[0].equals("A") && !datos[1].equals("B") && datos[1].contains("(")){
								String dir = datos[1].substring(1, datos[1].length());
								if(var.containsKey(dir)){
									System.out.println(AddADir(var.get(dir)));
								}
								else if(dir.equals("A")){
									System.out.println(AddADir(A));
								}
								else if(dir.equals("B")){
									System.out.println(AddABDir());
								}
								else{
									System.out.println(AddADir(Integer.parseInt(dir)));
								}
							}
							/*
							if(datos[0].equals("B") && !datos[1].equals("A") && datos[1].contains("(")){
								String dir = datos[1].substring(1, datos[1].length());
								if(var.containsKey(dir)){
									System.out.println(AddBDir(var.get(dir)));
								}
								else if(dir.equals("A")){
									System.out.println(AddBDir(A));
								}
								else if(dir.equals("B")){
									System.out.println(AddBDir(B));
								}
								else{
									System.out.println(AddBDir(Integer.parseInt(dir)));
								}
							}
							 */
						}
						else if(info[0].equals("ADD")){
							String[] datos = info[1].split(",");
							if(datos.length <= 1){
								String dir = datos[0].substring(1, datos[0].length());
								System.out.println(SubDir(Integer.parseInt(dir)));
							}
							else if(datos[0].equals("A") && datos[1].equals("B")){
								System.out.println(SubAB());
							}
							else if(datos[0].equals("B") && datos[1].equals("A")){
								System.out.println(SubBA());
							}
							else if(datos[0].equals("A") && !datos[1].equals("B") && !datos[1].contains("(")){
								if(var.containsKey(datos[1])){
									System.out.println(SubALit(var.get(datos[1])));
								}
								else{
									System.out.println(SubALit(Integer.parseInt(datos[1])));
								}
							}
							else if(datos[0].equals("B") && !datos[1].equals("A") && !datos[1].contains("(")){
								if(var.containsKey(datos[1])){
									System.out.println(SubBLit(var.get(datos[1])));
								}
								else{
									System.out.println(SubBLit(Integer.parseInt(datos[1])));
								}
							}
							if(datos[0].equals("A") && !datos[1].equals("B") && datos[1].contains("(")){
								String dir = datos[1].substring(1, datos[1].length());
								if(var.containsKey(dir)){
									System.out.println(SubADir(var.get(dir)));
								}
								else if(dir.equals("A")){
									System.out.println(SubADir(A));
								}
								else if(dir.equals("B")){
									System.out.println(SubABDir());
								}
								else{
									System.out.println(SubADir(Integer.parseInt(dir)));
								}
							}
						}
						else if(info[0].equals("INC")){
							if(info[1].equals("A")){
								System.out.println(IncA());
							}
							if(info[1].equals("B")){
								System.out.println(IncB());
							}
						}
						else if(info[0].equals("DEC")){
							if(info[1].equals("A")){
								System.out.println(DecA());
							}
						}
						else if(info[0].equals("JEQ")){
							System.out.println(JEQ(info[1]));
						}	
						else if(info[0].equals("JNE")){
								System.out.println(JNE(info[1]));
						}
						else if(info[0].equals("JMP")){
								System.out.println(JMP(info[1]));
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
		A += dir;
		String a = Integer.toBinaryString(dir);
		return "25Õb0000000 0010000010"+a;
	}

	public static String AddBLit(int dir){
		B += dir;
		String a = Integer.toBinaryString(dir);
		return "25Õb0000000 0000101011"+a;
	}

	public static String AddADir(int dir){
		A += mem[dir];
		String a = Integer.toBinaryString(dir);
		return "25Õb0000000 0000101011"+a;
	}

	public static String AddBDir(int dir){
		B += mem[dir];
		String a = Integer.toBinaryString(dir);
		return "25Õb0000000 0000101011"+a;
	}


	public static String AddDir(int dir){
		String a = Integer.toBinaryString(dir);
		return "25Õb00000 000000001011"+a;
	}

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
