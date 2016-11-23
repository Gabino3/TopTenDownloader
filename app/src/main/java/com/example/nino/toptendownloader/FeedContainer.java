package com.example.nino.toptendownloader;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class FeedContainer {

    @SerializedName("feed")
    private Feed feed;

    public FeedContainer() {}

    public Feed getFeed() {
        return feed;
    }

    public void setFeed(Feed feed) {
        this.feed = feed;
    }

    public static class Feed {

        @SerializedName("entry")
        private Entry[] entries;

        public Feed() {}

        public Entry[] getEntries() {
            return entries;
        }

        public void setEntries(Entry[] entries) {
            this.entries = entries;
        }
    }

    public static class Entry {

        public Entry() {}

        @SerializedName("im:name")
        private Label songName;

        @SerializedName("im:artist")
        private Label artist;

        @SerializedName("im:releaseDate")
        private Label releaseDate;

        public Label getSongName() {
            return songName;
        }

        public void setSongName(Label songName) {
            this.songName = songName;
        }

        public Label getArtist() {
            return artist;
        }

        public void setArtist(Label artist) {
            this.artist = artist;
        }

        public Label getReleaseDate() {
            return releaseDate;
        }

        public void setReleaseDate(Label releaseDate) {
            this.releaseDate = releaseDate;
        }

        @Override
        public String toString() {
            return "Song: " + songName.toString() + " Artist: " + artist.toString() +
                    " ReleaseDate: " + releaseDate.toString();
        }
    }


    public static class Label {

        public Label() {}

        private String label;

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        @Override
        public String toString() {
            return label;
        }
    }

    public static class EntryAdapter extends RecyclerView.Adapter<EntryAdapter.EntryViewHolder> {

        Entry[] entryList;

        public static class EntryViewHolder extends RecyclerView.ViewHolder {

            public TextView eTextView;

            public EntryViewHolder(TextView itemView) {
                super(itemView);
                eTextView = itemView;
            }
        }

        public EntryAdapter(Entry[] entryList) {
            this.entryList = entryList;
        }

        @Override
        public EntryAdapter.EntryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            // create a new view
            TextView v = (TextView) LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_view, parent, false);
            // set the view's size, margins, paddings and layout parameters

            EntryViewHolder vh = new EntryViewHolder(v);
            return vh;
        }

        @Override
        public void onBindViewHolder(EntryViewHolder holder, int position) {
            holder.eTextView.setText(entryList[position].toString());
        }


        @Override
        public int getItemCount() {
            return entryList.length;
        }


    }

}

