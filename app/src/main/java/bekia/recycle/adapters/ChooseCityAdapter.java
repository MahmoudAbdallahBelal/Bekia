package bekia.recycle.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import bekia.recycle.R;
import bekia.recycle.helper.Utils;
import bekia.recycle.requests.city.CityDetails;


public class ChooseCityAdapter extends RecyclerView.Adapter<ChooseCityAdapter.ChooseCountryHolder> implements Filterable {

    JSONArray countries;
    String cityNameSelected;
    OnCitySelectedListener listener;

    List<CityDetails> cityResponseList;
    List<CityDetails> cityFiltered;
    Context context;

    public ChooseCityAdapter(Context context ,List<CityDetails> cities, String cityNameSelected, OnCitySelectedListener listener){
        this.cityResponseList = cities;
        this.cityFiltered = cityResponseList;
        this.cityNameSelected = cityNameSelected;
        this.listener = listener;
        this.context = context;
    }


    @NonNull
    @Override
    public ChooseCountryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.adapter_choose_city, parent, false);
        return new ChooseCountryHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ChooseCountryHolder holder, final int position) {


        if(Utils.retrieveUserLanguage(context).equals("en")) {
            holder.txtName.setText(cityFiltered.get(position).getName());
            if(cityNameSelected !=null && cityNameSelected.toLowerCase().equals(cityFiltered.get(position).getName().toLowerCase())){
                holder.itemRow.setBackgroundColor(holder.itemView.getContext().getResources().getColor(R.color.colorGray));
            }else {
                holder.itemRow.setBackgroundColor(holder.itemView.getContext().getResources().getColor(R.color.colorWhite));
            }
            holder.itemRow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.getCitySelected(cityFiltered.get(position).getName(), cityFiltered.get(position).getId());

                }
            });

        }
      /*  else if(Utilities.getLanguage().equals("ar")){

                holder.txtName.setText(countriesFiltered.get(position).getName_ar());
                if (countryNameSelected != null && countryNameSelected.equals(countriesFiltered.get(position).getName_ar())) {
                    holder.itemRow.setBackgroundColor(holder.itemView.getContext().getResources().getColor(R.color.colorGray));
                } else {
                    holder.itemRow.setBackgroundColor(holder.itemView.getContext().getResources().getColor(R.color.colorWhite));
                }
                holder.itemRow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.getCountrySelected(countriesFiltered.get(position).getName_ar(), countriesFiltered.get(position).getId(), countriesFiltered.get(position).getPhonecode());

                    }
                });

        }*/

    }

    @Override
    public int getItemCount() {
        if (cityFiltered!= null)
            return cityFiltered.size();
        else
            return 0;    }

    @Override
    public Filter getFilter() {
        return new Filter() {

            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String charString = charSequence.toString();
                if(charString.isEmpty()){
                    cityFiltered = cityResponseList;
                }else {
                    if(Utils.retrieveUserLanguage(context).equals("en"))
                    {
                        List<CityDetails> filteredList = new ArrayList<>();
                        for (CityDetails c : cityResponseList){
                            if(c.getName().toLowerCase().contains(charString.toLowerCase())){
                                filteredList.add(c);
                            }
                        }
                        cityFiltered = filteredList;
                    }
                   /* else if(Utils.getLanguage().equals("ar")){
                        List<CountryResponse> filteredList = new ArrayList<>();
                        for (CountryResponse c : countryResponseList){
                            if(c.getName_ar().contains(charString)){
                                filteredList.add(c);
                            }
                        }
                        countriesFiltered = filteredList;
                    }*/
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = cityFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

                cityFiltered = (List<CityDetails>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ChooseCountryHolder extends RecyclerView.ViewHolder {

        TextView txtName;
        View itemRow;

        public ChooseCountryHolder(View itemView) {
            super(itemView);

            txtName = itemView.findViewById(R.id.adapter_choose_make_name);
            itemRow = itemView.findViewById(R.id.adapter_choose_make_item);
        }
    }

    public interface OnCitySelectedListener{
        void getCitySelected(String cityName, int cityId);
    }
}
