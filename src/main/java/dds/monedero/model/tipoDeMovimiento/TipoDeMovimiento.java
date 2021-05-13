package dds.monedero.model.tipoDeMovimiento;

public abstract class TipoDeMovimiento {
  public abstract Double realizarElMovimiento(Double monto);

  public abstract boolean isDeposito();
}
