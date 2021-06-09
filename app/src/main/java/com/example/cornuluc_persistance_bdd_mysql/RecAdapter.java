package com.example.cornuluc_persistance_bdd_mysql;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecAdapter extends RecyclerView.Adapter<RecAdapter.ViewHolder> {
private ArrayList<User> userList;

//Le viewHolder
public static class ViewHolder extends RecyclerView.ViewHolder {
    //Y declarer les objets de la vue qui seront chargés (par le tableau de données)
    public TextView Username;
    public TextView Email;
    public TextView Localite;

    //Constructeur du holder : le viewHolder a une reference vers tous les objets de la liste
    public ViewHolder(View v) {
        super(v);
        Username= (TextView) v.findViewById(R.id.textview_List_Username);
        Email = (TextView) v.findViewById(R.id.textview_List_Email);
        Localite = (TextView) v.findViewById(R.id.textview_List_Localite);
    }
}

    //Constructeur de l'adaptateur : initialisations de l’adapter et des données
    public RecAdapter(ArrayList<User> userList) {
        this.userList = userList;
    }

    //Chargement du layout et initialisation du viewHolder
    @Override
    public RecAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.canvas_row, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    //Lien entre viewHolder et données
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final User users = userList.get(position);
        holder.Username.setText(users.getUsername());
        holder.Email.setText(users.getEmail());
        holder.Localite.setText(users.getLocalite());
    }

    //Nombre d’éléments de la liste
    @Override
    public int getItemCount() {
        return userList.size();
    }
}//Fin de la classe de l’adaptateur

