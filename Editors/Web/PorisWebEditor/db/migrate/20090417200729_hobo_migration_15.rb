class HoboMigration15 < ActiveRecord::Migration
  def self.up
    add_column :project_memberships, :role_id, :integer
  end

  def self.down
    remove_column :project_memberships, :role_id
  end
end
