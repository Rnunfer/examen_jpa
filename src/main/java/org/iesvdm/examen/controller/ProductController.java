package org.iesvdm.examen.controller;

import lombok.extern.slf4j.Slf4j;
import org.iesvdm.examen.domain.Product;
import org.iesvdm.examen.dto.Addstatus;
import org.iesvdm.examen.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping(value={"","/"}, params={"!buscar", "!ordenar"})
    public List<Product> all() {
        log.info("Accediendo a todas los productos");
        return this.productService.all();
    }


    @GetMapping(value={"", "/"})
    public List<Product> all(@RequestParam("buscar") Optional<String> buscarOptional, @RequestParam("ordenar") Optional<String> ordenarOptional) {
        log.info("Accediendo a todas los productos con filtro buscar y ordenar");
        return this.productService.allFiltro(buscarOptional, ordenarOptional);
    }


    @PostMapping({"","/"})
    public Product newProduct(@RequestBody Product product) {
        return this.productService.save(product);
    }

    @GetMapping("/{id}")
    public Product one(@PathVariable("id") Long id) {
        return this.productService.one(id);
    }

    @PutMapping("/{id}")
    public Product replaceProduct(@PathVariable("id") Long id, @RequestBody Product product) {
        return this.productService.replace(id, product);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable("id") Long id) {
        this.productService.delete(id);
    }

    @GetMapping("/add/{user_id}/{product_id}")
    public Addstatus addToCart(@PathVariable("user_id") Long user_id, @PathVariable("product_id") Long product_id) {
        return this.productService.addToCart(user_id, product_id);
    }
}
