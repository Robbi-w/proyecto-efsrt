package com.visionclara.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.visionclara.model.Compra;
import com.visionclara.model.CompraDetalle;
import com.visionclara.repository.CompraDetalleRepository;
import com.visionclara.repository.CompraRepository;



@Service
public class CompraService {
	
	@Autowired
	private CompraRepository repoCompra;
	@Autowired 
	private CompraDetalleRepository repoCDetalle;
	
	// Listado de Compras
	public List<Compra> listarCompras() {
		return repoCompra.findAll();
	}
	// Buscar compra
	public Compra buscar(Integer cod) {
		return repoCompra.findById(cod).orElse(null);
	}
	// Eliminar Compra
	public void eliminar(Integer cod) {
		repoCompra.deleteById(cod);
	}
	
	// Transaccion registrar compra
	@Transactional
	public void registrarCompra(Compra bean) throws Exception {
		try {
			// GrabarCompra
			repoCompra.save(bean);		
			// GrabarCompraDetalle
			for(CompraDetalle cd:bean.getListaCDetalle()) {
				cd.setCodDetCom(bean.getCodCom());
				cd.getCDetalleProducto().getCodProd();
				repoCDetalle.save(cd);
			}
		} catch (Exception ex) {
	        throw new Exception("Se produjo un error al guardar la información de la compra o sus detalles. F", ex);
		}
	}
		// Actualizar(Estado)
	public void actualizar(Compra bean) {
		repoCompra.save(bean);
	}
}
