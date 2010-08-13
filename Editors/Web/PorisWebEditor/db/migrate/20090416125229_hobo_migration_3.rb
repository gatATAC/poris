class HoboMigration3 < ActiveRecord::Migration
  def self.up
    add_column :team_node_memberships, :team_id, :integer
    add_column :team_node_memberships, :node_id, :integer
  end

  def self.down
    remove_column :team_node_memberships, :team_id
    remove_column :team_node_memberships, :node_id
  end
end
