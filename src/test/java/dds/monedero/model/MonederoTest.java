package dds.monedero.model;

import dds.monedero.exceptions.MaximaCantidadDepositosException;
import dds.monedero.exceptions.MaximoExtraccionDiarioException;
import dds.monedero.exceptions.MontoNegativoException;
import dds.monedero.exceptions.SaldoMenorException;
import dds.monedero.model.tipoDeMovimiento.Deposito;
import dds.monedero.model.tipoDeMovimiento.Extraccion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class MonederoTest {
  private Cuenta cuenta;
  private Movimiento extraccion;
  private Movimiento deposito;

  @BeforeEach
  public void init() {
    cuenta = new Cuenta();
  }

  @Test
  public void laExtraccionSeInstaciaCorrectamente() {
    extraccion = new Movimiento(LocalDate.now(), 800.0, new Extraccion());

    assertTrue(extraccion.isExtraccion());
    assertTrue(extraccion.fueExtraido(LocalDate.now()));
  }

  @Test
  public void elDepositoSeInstaciaCorrectamente() {
    deposito = new Movimiento(LocalDate.now(), 500.0, new Deposito());

    assertTrue(deposito.isDeposito());
    assertTrue(deposito.fueDepositado(LocalDate.now()));
  }

  @Test
  public void poner() {
    cuenta.poner(1500.0);

    assertEquals(1500.0, cuenta.getSaldo());
  }

  @Test
  public void ponerMontoNegativo() {

    assertThrows(MontoNegativoException.class, () -> cuenta.poner(-1500.0));
  }

  @Test
  public void tresDepositos() {
    cuenta.poner(1500.0);
    cuenta.poner(456.0);
    cuenta.poner(1900.0);

    assertEquals(1500.0 + 456.0 + 1900.0, cuenta.getSaldo());
    assertEquals(3, cuenta.getMovimientos().size());
  }

  @Test
  public void masDeTresDepositos() {
    assertThrows(MaximaCantidadDepositosException.class, () -> {
          cuenta.poner(1500.0);
          cuenta.poner(456.0);
          cuenta.poner(1900.0);
          cuenta.poner(245.0);
    });
  }

  @Test
  public void extraerMasQueElSaldo() {
    assertThrows(SaldoMenorException.class, () -> {
          cuenta.setSaldo(90.0);
          cuenta.sacar(1001.0);
    });
  }

  @Test
  public void laExtraccionSeRealizaCorrectamente() {
    cuenta.poner(1500.0);
    cuenta.sacar(700.0);

    assertEquals(1500.00 - 700.0, cuenta.getSaldo());
  }

  @Test
  public void extraerMasDe1000() {
    assertThrows(MaximoExtraccionDiarioException.class, () -> {
      cuenta.setSaldo(5000.0);
      cuenta.sacar(1001.0);
    });
  }

  @Test
  public void ExtraerMontoNegativo() {

    assertThrows(MontoNegativoException.class, () -> cuenta.sacar(-500.0));
  }

}