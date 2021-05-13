package dds.monedero.model.tipoDeMovimiento;

public abstract class TipoDeMovimiento {
  public abstract Double saldoLuegoDeRealizarElMovimiento(Double saldoCuenta, Double monto);

  public abstract boolean isDeposito();
}
