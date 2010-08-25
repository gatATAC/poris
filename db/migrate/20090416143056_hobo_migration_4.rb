class HoboMigration4 < ActiveRecord::Migration
  def self.up
    create_table :roles do |t|
      t.string   :name
      t.datetime :created_at
      t.datetime :updated_at
    end
    
    add_column :team_memberships, :role_id, :integer
  end

  def self.down
    remove_column :team_memberships, :role_id
    
    drop_table :roles
  end
end
