package modulos;

import com.google.gson.JsonObject;

public class Divisa {
    private String target;
    private String base;
    private double resultado;
    private double cantidad;
    Monedas monedas;
    JsonObject jsonObject;

    public Divisa(Monedas monedas) {
        this.monedas = monedas;
    }

    public void setBase_code(String base_target, double cantidad) {
        this.cantidad = cantidad;
        this.base = base_target.substring(0,3);
        this.target = base_target.substring(3,6);
        this.jsonObject = this.monedas.conversion_rates();
        calcular();
    }

    private void calcular(){
        double valorTemporal = Double.parseDouble(String.valueOf(jsonObject.get(this.target))) / Double.parseDouble(String.valueOf(jsonObject.get(this.base)));
        this.resultado = this.cantidad * valorTemporal;
    }

    @Override
    public String toString() {
        return  """
                %.4f *%s equivale a: %.4f *%s""".formatted(this.cantidad, this.base, this.resultado, this.target);
    }

}
