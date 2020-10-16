package com.example.coronaStats;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

// This class is based on the class PersonAdapter given in the
// demo video about RecyclerView and Adapters from Lecture 3.
// I have used almost the same comments to make it clear what is doing what
// when I am going to look back into the code in the future.

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.CountryViewHolder> {

    // interface for handling country clicked
    public interface ICountryItemClickedListener{
        void onCountryClicked(int index);
    }

    // callback interface for user actions on clicked each item
    private ICountryItemClickedListener _listener;

    // data in the adapter
    private ArrayList<CountryModel> _countryList;

    //constructor
    public CountryAdapter(ICountryItemClickedListener listener){
        this._listener = listener;
    }

    // method to update the list, which will update the adapter/recyclerview
    public void updateCountryList(ArrayList<CountryModel> countryList){
        _countryList = countryList;
        notifyDataSetChanged(); // This notifies the adapter,
                                // and forces it to bind new viewholders depending on,
                                // where the users is in the application
    }

    // this is only called the first time the view holder is being created
    @NonNull
    @Override
    public CountryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View v;
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);
        CountryViewHolder vh = new CountryViewHolder(v,_listener); // listener can be removed, if we are not gonna act on interactions in the recycler view.
        return vh;
    }

    // This method is called when the user scroll in the view. The view holders are updated with correct data.
    @Override
    public void onBindViewHolder(@NonNull CountryViewHolder holder, int position) {
        holder.tvCountryName.setText(_countryList.get(position).getCountryName());
        holder.tvCases.setText(_countryList.get(position).getNbrOfCases());
        holder.tvDeaths.setText(_countryList.get(position).getNbrOfDeaths());
        holder.tvUserRating.setText(_countryList.get(position).getUserRating());
        holder.imgFlag.setImageResource(_countryList.get(position).getImgRessource());
    }

    // this returns the size of the the array, the user can scroll between.
    @Override
    public int getItemCount(){
        return _countryList.size();
    }

    // The ViewHolderClass for holding information about each list item in the recyclerview
    public class CountryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        // viewHolder ui widget references
        ImageView imgFlag;
        TextView tvCountryName;
        TextView tvCases;
        TextView tvDeaths;
        TextView tvUserRating;

        // custom callback interface for user actions done to the view holder item
        ICountryItemClickedListener listener;

        // constructor
        public CountryViewHolder(@NonNull View itemView, ICountryItemClickedListener countryItemClickedListener){
            super(itemView);

            // get references from the layout file
            imgFlag = itemView.findViewById(R.id.imgFlag);
            tvCases = itemView.findViewById(R.id.tvCases);
            tvDeaths = itemView.findViewById(R.id.tvDeaths);
            tvUserRating = itemView.findViewById(R.id.tvUserRating);
            tvCountryName = itemView.findViewById(R.id.tvCountryName);

            listener = countryItemClickedListener;

            // set click-listener for whole list item
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view){
            listener.onCountryClicked(getAdapterPosition());
        }
    }



}
