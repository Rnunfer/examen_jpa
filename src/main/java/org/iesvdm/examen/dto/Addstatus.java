package org.iesvdm.examen.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.iesvdm.examen.domain.Product;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Addstatus {

    private String status;

    private List<Product> listProduct = new ArrayList<>();

    private int total;
}
