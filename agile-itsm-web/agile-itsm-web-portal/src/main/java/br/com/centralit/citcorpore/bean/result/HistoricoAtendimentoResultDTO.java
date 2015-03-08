package br.com.centralit.citcorpore.bean.result;

import java.util.ArrayList;
import java.util.List;

import br.com.citframework.util.geo.Coordinate;

public class HistoricoAtendimentoResultDTO {

    private Coordinate start;
    private Coordinate finish;
    private String atendente;
    private Integer idAtendente;
    private List<Coordinate> steps;
    private List<Solicitation> solicitations;

    public Coordinate getStart() {
        return start;
    }

    public void setStart(final Coordinate start) {
        this.start = start;
    }

    public Coordinate getFinish() {
        return finish;
    }

    public void setFinish(final Coordinate finish) {
        this.finish = finish;
    }

    public String getAtendente() {
        return atendente;
    }

    public void setAtendente(final String atendente) {
        this.atendente = atendente;
    }

    public Integer getIdAtendente() {
        return idAtendente;
    }

    public void setIdAtendente(final Integer idAtendente) {
        this.idAtendente = idAtendente;
    }

    public List<Coordinate> getSteps() {
        if (steps == null) {
            steps = new ArrayList<>();
        }
        return steps;
    }

    public void setSteps(final List<Coordinate> steps) {
        this.steps = steps;
    }

    public List<Solicitation> getSolicitations() {
        if (solicitations == null) {
            solicitations = new ArrayList<>();
        }
        return solicitations;
    }

    public void setSolicitations(final List<Solicitation> solicitations) {
        this.solicitations = solicitations;
    }

    public class Solicitation {

        private Integer num;
        private String status;
        private String serv;
        private String desc;
        private String sol;
        private String unid;
        private Integer sit;
        private Integer hh;
        private Integer mm;

        private Coordinate coord;

        public Integer getNum() {
            return num;
        }

        public void setNum(final Integer num) {
            this.num = num;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(final String status) {
            this.status = status;
        }

        public String getServ() {
            return serv;
        }

        public void setServ(final String serv) {
            this.serv = serv;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(final String desc) {
            this.desc = desc;
        }

        public String getSol() {
            return sol;
        }

        public void setSol(final String sol) {
            this.sol = sol;
        }

        public String getUnid() {
            return unid;
        }

        public void setUnid(final String unid) {
            this.unid = unid;
        }

        public Integer getSit() {
            return sit;
        }

        public void setSit(final Integer sit) {
            this.sit = sit;
        }

        public Integer getHH() {
            return hh;
        }

        public void setHH(final Integer hh) {
            this.hh = hh;
        }

        public Integer getMM() {
            return mm;
        }

        public void setPrazoMM(final Integer mm) {
            this.mm = mm;
        }

        public Coordinate getCoord() {
            return coord;
        }

        public void setCoord(final Coordinate coord) {
            this.coord = coord;
        }

    }

}
