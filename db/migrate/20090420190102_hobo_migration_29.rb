class HoboMigration29 < ActiveRecord::Migration
  def self.up
    create_table :bugs do |t|
      t.string   :name
      t.text     :description
      t.boolean  :solved
      t.datetime :created_at
      t.datetime :updated_at
      t.integer  :owner_id
      t.integer  :modulo_id
    end
    
    create_table :modulos do |t|
      t.string   :name
      t.datetime :created_at
      t.datetime :updated_at
    end
  end

  def self.down
    drop_table :bugs
    drop_table :modulos
  end
end
