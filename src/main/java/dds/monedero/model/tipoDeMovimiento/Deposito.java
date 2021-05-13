package dds.monedero.model.tipoDeMovimiento;

public class Deposito extends TipoDeMovimiento{

  @Override
  public Double saldoLuegoDeRealizarElMovimiento(Double saldoCuenta, Double monto) {
    return saldoCuenta + monto;
  }

  @Override
  public boolean isDeposito(){
    return true;
  }
}
