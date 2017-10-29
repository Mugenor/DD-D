package main.entities;

import java.io.Serializable;

public class ParticipationPK implements Serializable {
    public Long person;
    public Long episode;
    public ParticipationPK(){}
    public ParticipationPK(Long id_person, Long id_episode){
        this.person = id_person;
        this.episode = id_episode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ParticipationPK that = (ParticipationPK) o;

        if (!person.equals(that.person)) return false;
        return episode.equals(that.episode);
    }

    @Override
    public int hashCode() {
        if(episode ==null || person ==null){
            return 0;
        } else {
            return person.hashCode() ^ episode.hashCode();
        }
    }
}
