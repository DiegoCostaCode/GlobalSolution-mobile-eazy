package com.example.gs_eazy

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.example.gs_eazy.Api.Api
import com.example.gs_eazy.Model.Conta
import org.json.JSONObject

class ContaAdapter(private val contas: MutableList<Conta>) :
    RecyclerView.Adapter<ContaAdapter.ContaViewHolder>() {

    class ContaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val kwhEditText: EditText = itemView.findViewById(R.id.et_kwh)
        val valorEditText: EditText = itemView.findViewById(R.id.et_valor)
        val dataEditText: EditText = itemView.findViewById(R.id.et_data)
        val updateButton: Button = itemView.findViewById(R.id.id_update_conta)
        val deleteButton: Button = itemView.findViewById(R.id.id_deletar_conta)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_conta, parent, false)
        return ContaViewHolder(view)
    }

    override fun onBindViewHolder(holder: ContaViewHolder, position: Int) {
        val conta = contas[position]

        holder.kwhEditText.setText(conta.kwh.toString())
        holder.valorEditText.setText(conta.valor.toString())
        holder.dataEditText.setText(conta.data)

        holder.kwhEditText.addTextChangedListener {
            conta.kwh = it.toString().toDoubleOrNull() ?: 0.0
        }
        holder.valorEditText.addTextChangedListener {
            conta.valor = it.toString().toDoubleOrNull() ?: 0.0
        }
        holder.dataEditText.addTextChangedListener {
            conta.data = it.toString()
        }


        holder.updateButton.setOnClickListener {
            val context = holder.itemView.context
            if (context is ContaActivity) {
                if (conta.id == 0) {
                    context.adicionarConta(conta)
                    Toast.makeText(context, "Conta criada com sucesso!", Toast.LENGTH_SHORT).show()
                } else {
                    context.updateConta(conta)
                    Toast.makeText(context, "Conta atualizada com sucesso!", Toast.LENGTH_SHORT).show()
                }
            }
        }

        holder.deleteButton.setOnClickListener {
            val context = holder.itemView.context
            if (context is ContaActivity) {
                context.removeConta(conta)
                Toast.makeText(context, "Conta deletada com sucesso!", Toast.LENGTH_SHORT).show()
            }
        }

    }


    override fun getItemCount(): Int = contas.size

    fun addConta(conta: Conta) {
        contas.add(conta)
        notifyItemInserted(contas.size - 1)
    }

    fun receberContas(contasFromApi: List<Conta>) {
        contas.clear()
        contas.addAll(contasFromApi)
        notifyDataSetChanged()
    }


}
