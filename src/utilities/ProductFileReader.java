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

/** I use the first line to size the Product array before reading the rows */
public class ProductFileReader {
    /** I read files through static methods, so I keep this constructor private */
    private ProductFileReader() {}

    /**
     * I read the declared number of products into an array
     * @param path absolute or relative file path
     * @return parsed products in file order
     * @throws IOException if the file cannot be opened or is not a regular file
     * @throws IllegalArgumentException if the path, count, rows, or prices are invalid
     */
    public static Product[] read(String path) throws IOException {
        // I check the rows and close the file after reading

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

    /**
     * I use the row type and prices to create the matching product
     * @param line product type and two prices separated by spaces
     * @param lineNumber file line used in error messages
     * @return product created from the row
     * @throws IllegalArgumentException if the type or prices are invalid
     */
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
