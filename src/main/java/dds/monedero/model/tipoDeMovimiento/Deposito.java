package dds.monedero.model.tipoDeMovimiento;

public class Deposito extends TipoDeMovimiento{

  @Override
  public Double realizarElMovimiento(Double monto) {
    return monto;
  }

  @Override
  public boolean isDeposito(){
    return true;
  }
}
