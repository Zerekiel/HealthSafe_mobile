package com.eipteam.healthsafe.nfc_manager.display;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.eipteam.healthsafe.R;

import java.util.ArrayList;

public class ListElementAdapter extends BaseAdapter {

    private final ArrayList<Element> listElem;
    private final Context context;

    public ListElementAdapter(Context _context, ArrayList<Element> _listElem) {
        context = _context;
        listElem = _listElem;
    }

    @Override
    public int getCount() {
        return listElem.size();
    }

    @Override
    public Object getItem(int position) {
        return listElem.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ListElementHolder holder;
        View newView = convertView;

        if (newView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            newView = inflater.inflate(R.layout.activity_custom_listview, parent, false);
            holder = new ListElementHolder();
            holder.label = newView.findViewById(R.id.labelText);
            holder.data = newView.findViewById(R.id.dataText);
            holder.edit = newView.findViewById(R.id.editButton);
            holder.delete = newView.findViewById(R.id.deleteButton);

            newView.setTag(holder);
        } else {
            holder = (ListElementHolder) newView.getTag();
        }

        holder.label.setText(listElem.get(position).getText());
        holder.label.setId(position);

        if (listElem.get(position).getData().equals("N/A"))
            listElem.get(position).setData("");
        holder.data.setText(listElem.get(position).getData());
        holder.data.setId(position);

        holder.edit.setId(position);
        holder.delete.setId(position);
/*
        holder.data.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!listElem.isEmpty()) {
                    final int position = v.getId();
                    final EditText Caption = (EditText) v;
                    listElem.get(position).setData(Caption.getText().toString());
                    notifyDataSetChanged();
                }
            }
        });
*/

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!listElem.isEmpty()) {
                    final int position = v.getId();
                    String caption = listElem.get(position).getData();
                    final EditText input = new EditText(context);
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT);
                    input.setLayoutParams(lp);
                    input.setText(caption);

                    AlertDialog.Builder alert = new AlertDialog.Builder(context);
                    alert.setTitle("EDIT");
                    alert.setMessage("Enter new value:");
                    alert.setView(input);
                    alert.setIcon(R.drawable.ic_edit_24px);

                    alert.setPositiveButton("DONE", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            listElem.get(position).setData(input.getText().toString());
                        }
                    });

                    alert.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

                    alert.show();

                    notifyDataSetChanged();
                }
            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!listElem.isEmpty()) {
                    final int position = v.getId();
                    listElem.get(position).setData("");
                    notifyDataSetChanged();
                }
            }
        });


        return newView;
    }
}
