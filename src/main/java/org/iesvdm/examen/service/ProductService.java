package org.iesvdm.examen.service;

import org.iesvdm.examen.Repository.Cart_itemRepository;
import org.iesvdm.examen.Repository.ProductRepository;
import org.iesvdm.examen.Repository.UserRepository;
import org.iesvdm.examen.domain.Cart_item;
import org.iesvdm.examen.domain.Product;
import org.iesvdm.examen.domain.User;
import org.iesvdm.examen.dto.Addstatus;
import org.iesvdm.examen.exception.ProductNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    private Cart_itemRepository cartItemRepository;

    @Autowired
    private UserRepository userRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> all() {
        return this.productRepository.findAll();
    }

    public List<Product> allFiltro(Optional<String> buscarOptional, Optional<String> ordenarOptional) {

        List<Product> listProducts = new ArrayList<>();
        if(buscarOptional.isPresent() && ordenarOptional.isEmpty()) {
            listProducts = this.productRepository.findByNameContainingIgnoreCase(buscarOptional.get());
        } else if (buscarOptional.isPresent() && ordenarOptional.isPresent() && ordenarOptional.get().equals("asc")) {
            listProducts = this.productRepository.findByNameContainingIgnoreCaseOrderByNameAsc(buscarOptional.get());
        } else if (buscarOptional.isPresent() && ordenarOptional.isPresent() && ordenarOptional.get().equals("desc")) {
            listProducts = this.productRepository.findByNameContainingIgnoreCaseOrderByNameDesc(buscarOptional.get());
        } else if (buscarOptional.isEmpty() && ordenarOptional.isPresent() && ordenarOptional.get().equals("desc")) {
            listProducts = this.productRepository.findAllByOrderByNameDesc();
        } else if (buscarOptional.isEmpty() && ordenarOptional.isPresent() && ordenarOptional.get().equals("asc")) {
            listProducts = this.productRepository.findAllByOrderByNameAsc();
        } else {
            listProducts = this.productRepository.findAll();
        }
        return listProducts;
    }
    
    public Product save(Product product) {
        return this.productRepository.save(product);
    }

    public Product one(Long id) {
        return this.productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    public Product replace(Long id, Product product) {

        return this.productRepository.findById(id).map(p -> (id.equals(product.getProduct_id())  ?
                        this.productRepository.save(product) : null))
                .orElseThrow(() -> new ProductNotFoundException(id));

    }

    public void delete(Long id) {
        this.productRepository.findById(id).map(p -> {this.productRepository.delete(p);
                    return p;})
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    public Addstatus addToCart(Long user_id, Long product_id) {
        Addstatus addStatus = new Addstatus();

        if (this.productRepository.findById(product_id).get() != null) {
            Product product = this.productRepository.findById(product_id).get();
            if(this.userRepository.findById(user_id).get() != null) {
                User user = this.userRepository.findById(user_id).get();
                boolean nuevo = true;
                int i = 0;
                List<Product> listProd = this.productRepository.queryAddToCart(user.getUser_id());
                if (listProd.size() != 0) {
                    do {
                        if (product.getName().equals(listProd.get(i).getName())) {
                            nuevo = false;
                        } else {
                            i++;
                        }
                    } while (i < listProd.size() && nuevo);
                }

                if (nuevo) {
                    Cart_item cart_item = new Cart_item();
                    cart_item.setQuantity(Long.parseLong("1"));
                    cart_item.setUser(user);
                    cart_item.setProduct(product);
                    this.cartItemRepository.save(cart_item);
                    addStatus.setStatus("Producto añadido a carrito");
                } else {
                    Cart_item cart_item = user.getCart_items().get(i);
                    cart_item.setProduct(product);
                    user.getCart_items().get(i).setQuantity(user.getCart_items().get(i).getQuantity()+Long.parseLong("1"));
                    addStatus.setStatus("Producto modificado en carrito");
                }
                addStatus.setListProduct(listProd);
                addStatus.setTotal(listProd.size());
            } else {
                addStatus.setStatus("No se ha encontrado el usuario");
            }
        } else {
            addStatus.setStatus("No se ha encontrado el producto");
        }
        return addStatus;
    }
}
