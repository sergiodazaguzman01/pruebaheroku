package co.com.eam.controller;

import javax.validation.Valid;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

 
import co.com.eam.domain.Producto;
 
import co.com.eam.repository.IBodegaRepo;
import co.com.eam.repository.IProductoRepo;
import co.com.eam.repository.IProveedorRepo;
import co.com.eam.repository.ISubCategoriaRepo;


@Controller
 
public class ProductoController {
	@Autowired
	private IProductoRepo iProductoRepo;
	 
	@Autowired
	private  IBodegaRepo iBodegaRepo;
	@Autowired
	private IProveedorRepo iProveedorRepo;
	@Autowired
	private ISubCategoriaRepo iSubcategoriaRepo;	

	    
	@GetMapping("addProducto")
    public String showSignUpForm(Producto producto, Model model) {
		model.addAttribute("bodegas", iBodegaRepo.findAll());
		model.addAttribute("proveedors", iProveedorRepo.findAll());
		model.addAttribute("subcategorias", iSubcategoriaRepo.findAll());
     
        return "add-producto";
    }

	 @PostMapping("add_producto")
	    public String addProveedor(Producto producto, BindingResult result, Model model) {
	        if (result.hasErrors()) {
	        	 
	         	
	            return "add-producto";
	        }
	        
	        iProductoRepo.save(producto);
	        model.addAttribute("productos", iProductoRepo.findAll());
	        return "listarProducto";
	    }
	
    
	 @GetMapping("/editProducto/{id_producto}")
	   public String showUpdateForm(@PathVariable("id_producto") int idProducto, Model model) {
	   	Producto producto = iProductoRepo.findById(idProducto).orElseThrow(() -> new IllegalArgumentException("Invalid categoria id:" + idProducto));
	       model.addAttribute("producto", producto);
	       model.addAttribute("bodegas", iBodegaRepo.findAll());
			model.addAttribute("proveedors", iProveedorRepo.findAll());
			model.addAttribute("subcategorias", iSubcategoriaRepo.findAll());
	       return "update-producto";
	   }
	   
	   @PostMapping("/updateProducto/{id_producto}")
	   public String updateProducto(@PathVariable("id_producto") int idProducto, @Valid Producto producto, BindingResult result, Model model) {
	       if (result.hasErrors()) {
	       	producto.setId_producto(idProducto);
	           return "update-producto";
	       }
	       
	       iProductoRepo.save(producto);
	       model.addAttribute("productos", iProductoRepo.findAll());
	       return "listarProducto";
	   }
    
    @GetMapping("/deleteProducto/{id_producto}")
    public String deleteProducto(@PathVariable("id_producto") int idProducto, Model model) {
        Producto producto = iProductoRepo.findById(idProducto).orElseThrow(() -> new IllegalArgumentException("Invalid producto id:" + idProducto));
        iProductoRepo.delete(producto);
    	model.addAttribute("productos", iProductoRepo.findAll());
        return "listarproducto";
    }
    @GetMapping("/listarProducto")
    public String ListarProducto(Model model) {
    	 
        model.addAttribute("productos", iProductoRepo.findAll());
        return "listarProducto";
    }
     

}