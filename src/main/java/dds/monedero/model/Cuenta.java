package dds.monedero.model;

import dds.monedero.exceptions.MaximaCantidadDepositosException;
import dds.monedero.exceptions.MaximoExtraccionDiarioException;
import dds.monedero.exceptions.MontoNegativoException;
import dds.monedero.exceptions.SaldoMenorException;
import dds.monedero.model.tipoDeMovimiento.Deposito;
import dds.monedero.model.tipoDeMovimiento.Extraccion;
import dds.monedero.model.tipoDeMovimiento.TipoDeMovimiento;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Cuenta {

  private Double saldo;
  private List<Movimiento> movimientos = new ArrayList<>();

  public Cuenta() {
    saldo = 0.0;
  }

  public Cuenta(Double montoInicial) {
    saldo = montoInicial;
  }

  public void setMovimientos(List<Movimiento> movimientos) {
    this.movimientos = movimientos;
  }

  public void poner(Double cuanto) {
    if (cuanto <= 0) {
      throw new MontoNegativoException(cuanto + ": el monto a ingresar debe ser un valor positivo");
    }

    if (getMovimientos().stream().filter(movimiento -> movimiento.isDeposito()).count() >= 3) {
      throw new MaximaCantidadDepositosException("Ya excedio los " + 3 + " depositos diarios");
    }

    this.agregarMovimiento(LocalDate.now(), cuanto, new Deposito());
  }

  public void sacar(Double cuanto) {
    this.validarSiSePuedeExtraerMonto(cuanto);
    Double montoExtraidoHoy = getMontoExtraidoA(LocalDate.now());
    Double limite = 1000 - montoExtraidoHoy;

    if (cuanto > limite) {
      throw new MaximoExtraccionDiarioException("No puede extraer mas de $ " + 1000
          + " diarios, límite: " + limite);
    }
    this.agregarMovimiento(LocalDate.now(), cuanto, new Extraccion());
  }

  public void validarSiSePuedeExtraerMonto(Double cuanto) {
    if (cuanto <= 0) {
      throw new MontoNegativoException(cuanto + ": el monto a ingresar debe ser un valor positivo");
    }
    if (getSaldo() - cuanto < 0) {
      throw new SaldoMenorException("No puede sacar mas de " + getSaldo() + " $");
    }
  }

  public void agregarMovimiento(LocalDate fecha, Double cuanto, TipoDeMovimiento tipoDeMovimiento) {
    Movimiento movimiento = new Movimiento(fecha, cuanto, tipoDeMovimiento);
    this.realizarMovimiento(movimiento);
    movimientos.add(movimiento);
  }

  private void realizarMovimiento(Movimiento movimiento) {
    this.saldo = saldo + movimiento.calcularValor();
  }

  public Double getMontoExtraidoA(LocalDate fecha) {
    return getMovimientos().stream()
        .filter(movimiento -> movimiento.fueExtraido(fecha))
        .mapToDouble(Movimiento::getMonto)
        .sum();
  }

  public List<Movimiento> getMovimientos() {
    return movimientos;
  }

  public Double getSaldo() {
    return saldo;
  }

  public void setSaldo(Double saldo) {
    this.saldo = saldo;
  }

}
