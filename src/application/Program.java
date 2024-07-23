package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import entities.Product;

public class Program {

	public static void main(String[] args) {
		
		/*
		 * Problema: Ler um conjunto de produtos a partir de um arquivo em formato csv
		 * Supondo que exist pelo menos um produto. Em seguida mostrar o preço médio dos
		 * produtos, depois mostrar os nomes, em ordem decrescente, dos produtos que possuem 
		 * preço inferior ao preço médio.
		 */
		Locale.setDefault(Locale.US);
		Scanner sc = new Scanner(System.in);
		System.out.println("Digite o caminho do arquivo: ");
		String path = sc.next();
		try (BufferedReader bf = new BufferedReader(new FileReader(path))) {
			String line = bf.readLine();
			List<Product> list  = new ArrayList<>();			
			while (line != null) {
				String[] fields = line.split(",");
				list.add(new Product(fields[0], Double.parseDouble(fields[1])));
				line = bf.readLine();
			}
			
			double avg = list.stream()
					.map(p -> p.getPrice())
					.reduce(0.0,(x , y) -> x + y) 
					/ list.size();
			
			System.out.println("Preço médio: " + String.format("%.2f", avg));
			
			Comparator<String> comp = (s1, s2) -> s1.toUpperCase().compareTo(s2.toUpperCase());
			
			List<String> names = list.stream()
					.filter(p -> p.getPrice() < avg)
					.map(p -> p.getName())
					.sorted(comp.reversed()).toList();
			
			names.forEach(System.out::println);
		}
		catch (IOException e) {
			System.out.println("Error: " + e.getMessage());
		}
		sc.close();
	}

}
