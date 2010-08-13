class HoboMigration9 < ActiveRecord::Migration
  def self.up
    add_column :project_memberships, :contributor, :boolean
  end

  def self.down
    remove_column :project_memberships, :contributor
  end
end
