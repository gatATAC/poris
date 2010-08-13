class HoboMigration32 < ActiveRecord::Migration
  def self.up
    add_column :projects, :abbrev, :string
    
    add_column :roles, :abbrev, :string
    
    remove_column :task_assignments, :role_id
    
    add_column :users, :abbrev, :string
  end

  def self.down
    remove_column :projects, :abbrev
    
    remove_column :roles, :abbrev
    
    add_column :task_assignments, :role_id, :integer
    
    remove_column :users, :abbrev
  end
end
