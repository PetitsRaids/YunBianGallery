package com.raids.gallery.model.database.datamodel;

import java.util.List;
import java.util.Objects;

public class PictureBackData {
    private List<Hit> hits;

    public List<Hit> getHits() {
        return hits;
    }

    public void setHits(List<Hit> hits) {
        this.hits = hits;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PictureBackData that = (PictureBackData) o;
        return Objects.equals(hits, that.hits);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hits);
    }

    /*
    {
    "total":23826,
    "totalHits":500,
    "hits":[
        {

        },
        {
            "id":3113318,
     */
}
