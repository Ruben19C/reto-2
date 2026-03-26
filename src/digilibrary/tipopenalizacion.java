package digilibrary;

public class tipopenalizacion {
	private int cod_penalizacion;
	   private String motivo;
	   private int num_dias_penalizacion;


	   public tipopenalizacion(int cod_penalizacion, String motivo, int num_dias_penalizacion) {
	      this.cod_penalizacion = cod_penalizacion;
	      this.motivo = motivo;
	      this.num_dias_penalizacion = num_dias_penalizacion;
	   }


	   public int getCod_penalizacion() {
	      return this.cod_penalizacion;
	   }


	   public void setCod_penalizacion(int cod_penalizacion) {
	      this.cod_penalizacion = cod_penalizacion;
	   }


	   public String getMotivo() {
	      return this.motivo;
	   }


	   public void setMotivo(String motivo) {
	      this.motivo = motivo;
	   }


	   public int getNum_dias_penalizacion() {
	      return this.num_dias_penalizacion;
	   }


	   public void setNum_dias_penalizacion(int num_dias_penalizacion) {
	      this.num_dias_penalizacion = num_dias_penalizacion;
	   }


	   public int calcularPenalizacion(int diasRetraso) {
	      return diasRetraso > 0 ? this.num_dias_penalizacion : 0;
	   }

}
