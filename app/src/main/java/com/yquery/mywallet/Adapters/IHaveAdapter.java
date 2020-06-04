package com.yquery.mywallet.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yquery.mywallet.Entities.IHaveEntity;
import com.yquery.mywallet.R;

import java.util.List;

public class IHaveAdapter extends RecyclerView.Adapter<IHaveAdapter.IHaveViewHolder>{

    private Context mCtx;
    private List<IHaveEntity> haveEntityList;

    public IHaveAdapter(Context mCtx, List<IHaveEntity> haveEntityList) {
        this.mCtx = mCtx;
        this.haveEntityList = haveEntityList;
    }

    @NonNull
    @Override
    public IHaveViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new IHaveViewHolder(LayoutInflater.from(mCtx).inflate(R.layout.i_have_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull IHaveViewHolder holder, int position) {
        final IHaveEntity haveEntity = haveEntityList.get(position);

        if (haveEntity.isAdded()){
            holder.money.setText("+ " + haveEntity.getEnteredMoney());
        }else {
            holder.money.setText("- " + haveEntity.getEnteredMoney());
        }

        holder.detail.setText(haveEntity.getDetails());

        holder.dateandtime.setText(haveEntity.getDate() + " , " + haveEntity.getTime());

    }

    @Override
    public int getItemCount() {
        return haveEntityList.size();
    }

    class IHaveViewHolder extends RecyclerView.ViewHolder{

        TextView money, detail, dateandtime;

        public IHaveViewHolder(@NonNull View itemView) {
            super(itemView);

            detail = itemView.findViewById(R.id.details);
            money = itemView.findViewById(R.id.money);
            dateandtime = itemView.findViewById(R.id.dateAndTime);

        }
    }

}
