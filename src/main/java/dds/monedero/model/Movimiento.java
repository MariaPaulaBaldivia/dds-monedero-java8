package dds.monedero.model;

import dds.monedero.model.tipoDeMovimiento.TipoDeMovimiento;

import java.time.LocalDate;

public class Movimiento {
  private LocalDate fecha;
  //En ningún lenguaje de programación usen jamás doubles para modelar dinero en el mundo real
  //siempre usen numeros de precision arbitraria, como BigDecimal en Java y similares
  private Double monto;
  private TipoDeMovimiento tipoDeMovimiento;

  public Movimiento(LocalDate fecha, Double monto, TipoDeMovimiento tipoDeMovimiento) {
    this.fecha = fecha;
    this.monto = monto;
    this.tipoDeMovimiento = tipoDeMovimiento;
  }

  public Double getMonto() {
    return monto;
  }

  public LocalDate getFecha() {
    return fecha;
  }

  public boolean fueDepositado(LocalDate fecha) {
    return tipoDeMovimiento.isDeposito() && esDeLaFecha(fecha);
  }

  public boolean fueExtraido(LocalDate fecha) {
    return isExtraccion() && esDeLaFecha(fecha);
  }

  public boolean esDeLaFecha(LocalDate fecha) {
    return this.fecha.equals(fecha);
  }

  public boolean isDeposito() {
    return tipoDeMovimiento.isDeposito();
  }

  public boolean isExtraccion() {
    return ! tipoDeMovimiento.isDeposito();
  }

  public void agregateA(Cuenta cuenta) {
    cuenta.setSaldo(calcularValor(cuenta));
    cuenta.agregarMovimiento(fecha, monto, tipoDeMovimiento);
  }

  public Double calcularValor(Cuenta cuenta) {
    return tipoDeMovimiento.saldoLuegoDeRealizarElMovimiento(cuenta.getSaldo(), this.getMonto());
  }
}
