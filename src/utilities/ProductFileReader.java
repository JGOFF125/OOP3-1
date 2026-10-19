package utilities;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import products.Product;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import products.Dresses;
import products.Jackets;
import products.Jeans;
import products.Product;
import products.Shoes;
import products.Shorts;
import products.Skirts;
import products.TShirts;

/**  Member 1 completes this class */
public class ProductFileReader {
    /**
     * Allocates a Product array using the first line, then reads its products.
     * @param path absolute or relative file path
     * @return array of parsed products
     * @throws IOException when the file cannot be read or its data is invalid
     */
	
	private ProductFileReader() {}
	
    public static Product[] read(String path) throws IOException {
        // Member 1: use an array, validate rows, and close the file
    	
    	int productCount;
    	
    	if (path == null || path.isBlank() ) {
    		throw new IllegalArgumentException("File path cannot be empty.");
    	}
    	
    	Path filePath = Path.of(path);
    	
    	if (!Files.exists(filePath)) {
    		throw new IOException("File does not exist: " + path);
    	}
    	
    	if (!Files.isRegularFile(filePath)) {
    		throw new IOException("Path is not a file: " + path);
    	}
    	
    	try (Scanner sc = new Scanner(filePath)){
    		if (!sc.hasNextLine()) {
    			throw new IllegalArgumentException("The product file is empty.");
    		}
    		
    		try {
    			productCount = Integer.parseInt(sc.nextLine().trim());
    		} catch (NumberFormatException exception) {
    			throw new IllegalArgumentException(
    					"The first line must contain a valid product count.",
    					exception
				);
    		}
    		
    		if (productCount <= 0) {
    			throw new IllegalArgumentException("Product count needs to be at least one (1).");
    		}
    		
    		Product[] products = new Product[productCount];
    		int index = 0;
    		int lineNumber = 1;
    		
    		while (sc.hasNextLine()) {
    			String line = sc.nextLine().trim();
    			lineNumber++;
    			
    			if(line.isEmpty()) {
    				continue;
    			}
    			
    			if(index >= productCount) {
    				throw new IllegalArgumentException("The file contains more products than declared.");
    			}
    			
    			products[index] = parseProduct(line, lineNumber);
    			index++;
    		}
    		
    		if(index != productCount) {
    			throw new IllegalArgumentException(
    					"Expected " + productCount + " products, but found " + index + "."
				);
    		}
    		
    		return products;
    	}
    	
    }
    
    private static Product parseProduct(String line, int lineNumber) {
    	String[] fields = line.split("\\s+");
    	double originalPrice;
    	double updatedPrice;
    	Product product;
    	String productType;
    	
    	if (fields.length != 3) {
    		throw new IllegalArgumentException(
    				"Invalid data on line " + lineNumber + ": expected product type and two prices."
			);
    	}
    	
    	try {
    		originalPrice = Double.parseDouble(fields[1]);
    		updatedPrice = Double.parseDouble(fields[2]);
    	} catch (NumberFormatException exception) {
    		throw new IllegalArgumentException(
    				"Invalid price on line " + lineNumber + ".",
    				exception
			);
    	}
    	
    	productType = fields[0].toLowerCase();
    	product = switch(productType) {
    		case "jeans" -> new Jeans(originalPrice, updatedPrice);
    		case "tshirts" -> new TShirts(originalPrice, updatedPrice);
    		case "skirts" -> new Skirts(originalPrice, updatedPrice);
    		case "dresses" -> new Dresses(originalPrice, updatedPrice);
    		case "shorts" -> new Shorts(originalPrice, updatedPrice);
    		case "jackets" -> new Jackets(originalPrice, updatedPrice);
    		case "shoes" -> new Shoes(originalPrice, updatedPrice);
    		default -> throw new IllegalArgumentException("Unknown product type '" + productType + "' on line " + lineNumber + ".");
    		
    	};
    	
    	return product;
    }
}
