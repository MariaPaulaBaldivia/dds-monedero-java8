package dds.monedero.model.tipoDeMovimiento;

public class Extraccion extends TipoDeMovimiento {

  @Override
  public Double realizarElMovimiento(Double monto) {
    return - monto;
  }
  @Override
  public boolean isDeposito(){
    return false;
  }
}
