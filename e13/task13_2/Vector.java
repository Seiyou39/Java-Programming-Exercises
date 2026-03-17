//15824071 ZHANG JINGYANG
package task13_2;

import java.io.FileWriter;
import java.io.PrintWriter;

public class Vector {
	
	private double[] data;
	
	Vector(double[] data) {
        this.data = data;
    }
	
	void show() {
		for(int i = 0;  i < data.length; i++) {
			if(i == data.length - 1) {
				System.out.print(data[i]);
			}
			else{
				System.out.print(data[i] + " ");
			}
		}
		System.out.println();
	}
	
	void show(String description) {
		System.out.println(description);
		for(int i = 0;  i < data.length; i++) {
			if(i == data.length - 1) {
				System.out.print(data[i]);
			}
			else{
				System.out.print(data[i] + " ");
			}
		}
		System.out.println();
	}
	
	//ベクトル同士の引き算
	static Vector sub(Vector a, Vector b) {
		double[] sub_result = new double[a.data.length];
		
		for(int i = 0; i < a.data.length; i++) {
			sub_result[i] = a.data[i] - b.data[i];
		}
		
		Vector sub = new Vector(sub_result);
		
		return sub;
	}
	
	//getter
	double getter(int i) {
		
		return data[i];
	}
	
	void  writeToFile(String filename) {
		writeToFile(filename,false);
	}
	
	void writeToFile(String filename, boolean append) {
   	 try {
            PrintWriter pw = new PrintWriter(new FileWriter(filename, append));
            for (int i = 0; i < data.length; i++) {
            	if(i == data.length - 1) {
            		pw.print(data[i]);
            	}
            	else{
            		pw.print(data[i] + " ");
            	}
            }
            pw.println();
            pw.close();
        } catch (Exception e) {
            System.out.println(e);
        }
   }

}
