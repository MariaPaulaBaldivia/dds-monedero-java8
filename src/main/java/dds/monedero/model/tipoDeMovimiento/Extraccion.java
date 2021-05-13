package dds.monedero.model.tipoDeMovimiento;

public class Extraccion extends TipoDeMovimiento {

  @Override
  public Double saldoLuegoDeRealizarElMovimiento(Double saldoCuenta, Double monto) {
    return saldoCuenta - monto;
  }
  @Override
  public boolean isDeposito(){
    return false;
  }
}
