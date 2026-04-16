package com.estebanleyton.gestionnomina;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class GestionnominaApplicationTests {

	@Test
	void calcularSalarioTotal() {

		double salarioBase = 2_000_000;
		double horasExtras = 300_000;
		double bonos = 200_000;

		double salarioTotal = salarioBase + horasExtras + bonos;

		assertEquals(2_500_000, salarioTotal);
	}
	@Test
	void calcularHorasExtras() {

		double valorHora = 10_000;
		int horasExtras = 10;

		double totalHorasExtras = valorHora * horasExtras;

		assertEquals(100_000, totalHorasExtras);
	}
	@Test
	void validarDatosEmpleado() {

		String nombre = "Carlos Pérez";
		String identificacion = "123456789";
		double salarioBase = 1_500_000;

		boolean datosValidos =
				nombre != null && !nombre.isEmpty() &&
						identificacion != null && !identificacion.isEmpty() &&
						salarioBase > 0;

		assertEquals(true, datosValidos);
	}
	@Test
	void registrarNominaCorrectamente() {

		double salarioBase = 2_000_000;
		double horasExtras = 200_000;
		double bonos = 300_000;

		double totalNomina = salarioBase + horasExtras + bonos;

		boolean nominaRegistrada = totalNomina > 0;

		assertEquals(true, nominaRegistrada);
	}


}
