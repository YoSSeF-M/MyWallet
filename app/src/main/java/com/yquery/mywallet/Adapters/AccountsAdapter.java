package com.yquery.mywallet.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yquery.mywallet.Activities.AccountsActivity;
import com.yquery.mywallet.Entities.AccountsEntity;

import com.yquery.mywallet.R;

import java.util.List;

public class AccountsAdapter extends RecyclerView.Adapter<AccountsAdapter.AccountsViewHolder> {

    private Context mCtx;
    private List<AccountsEntity> accountsEntityList;

    public AccountsAdapter(Context mCtx, List<AccountsEntity> accountsEntityList) {
        this.mCtx = mCtx;
        this.accountsEntityList = accountsEntityList;
    }

    @NonNull
    @Override
    public AccountsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AccountsViewHolder(LayoutInflater.from(mCtx).inflate(R.layout.accounts_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AccountsViewHolder holder, int position) {
        final AccountsEntity accountsEntity = accountsEntityList.get(position);

        holder.name.setText(accountsEntity.getName());
        holder.value.setText(accountsEntity.getValue() + " EGP");

    }

    @Override
    public int getItemCount() {
        return accountsEntityList.size();
    }

    public class AccountsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView name, value;

        public AccountsViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.accountNameEditTextListItem);
            value = itemView.findViewById(R.id.accountValueEditTextListItem);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            AccountsEntity accountsEntity = accountsEntityList.get(getAdapterPosition());

            Intent intent = new Intent(mCtx, AccountsActivity.class);
            intent.putExtra("account",  accountsEntity);

            mCtx.startActivity(intent);

        }
    }
}
