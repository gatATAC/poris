class HoboMigration25 < ActiveRecord::Migration
  def self.up
    remove_column :labels, :scope_kind_id
  end

  def self.down
    add_column :labels, :scope_kind_id, :integer
  end
end
