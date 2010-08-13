class HoboMigration44 < ActiveRecord::Migration
  def self.up
    add_index :nodes_edges, [:source_id]
    add_index :nodes_edges, [:destination_id]
  end

  def self.down
    remove_index :nodes_edges, :name => :index_nodes_edges_on_source_id rescue ActiveRecord::StatementInvalid
    remove_index :nodes_edges, :name => :index_nodes_edges_on_destination_id rescue ActiveRecord::StatementInvalid
  end
end
