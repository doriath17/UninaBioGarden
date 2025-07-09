package uninabiogarden.entities;

public class Lotto {
    private String orto;
    private String numLotto;
    private Double extension;

    public Lotto(String orto, String numLotto, Double extension) {
        this.orto = orto;
        this.numLotto = numLotto;
        this.extension = extension;
    }

    public String getOrto() {
        return orto;
    }

    public void setOrto(String orto) {
        this.orto = orto;
    }

    public String getNumLotto() {
        return numLotto;
    }

    public void setNumLotto(String numLotto) {
        this.numLotto = numLotto;
    }

    public Double getExtension() {
        return extension;
    }

    public void setExtension(Double extension) {
        this.extension = extension;
    }
}
