class HoboMigration47 < ActiveRecord::Migration
  def self.up
    create_table :value_formatters do |t|
      t.string   :name
      t.datetime :created_at
      t.datetime :updated_at
    end

    add_column :nodes, :value_formatter_id, :integer

    add_index :nodes, [:value_formatter_id]

    formatters = [
                  {:name=>'none'},
                  {:name=>'RA(HH:MM:SS)'},
                  {:name=>'DEC(DD:MM:SS)'},
            ]

    formatters.each { |nt| ValueFormatter.create :name => nt.fetch(:name)}

  end

  def self.down
    remove_column :nodes, :value_formatter_id

    drop_table :value_formatters

    remove_index :nodes, :name => :index_nodes_on_value_formatter_id rescue ActiveRecord::StatementInvalid
  end
end
