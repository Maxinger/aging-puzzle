create or replace view review_summary as

select t.base_entity_id as id,
       t.base_entity_id,
       t.entity_type,
       t.name as entity_name,
       t.language,
       t.translations,
       t.reviews_count,
       datediff(curdate(), coalesce(t.last_review_date, t.date_created, '2019-10-01')) as days_waiting
from (
     select p.base_entity_id, 'Project' as entity_type, p.name, bp.date_created,
            min(p.language) as language, count(p.language) as translations,
            count(distinct r.id) as reviews_count,
            max(r.date) as last_review_date
     from project p
          join base_project bp on p.base_entity_id = bp.id
          left outer join review r on p.base_entity_id = r.base_project_id
     group by base_entity_id

     union

     select o.base_entity_id,
            'Organization' as entity_type, o.name, bo.date_created,
            min(o.language) as language, count(o.language) as translations,
            count(distinct r.id) as reviews_count, max(r.date) as last_review_date
     from organization o
          join base_organization bo on o.base_entity_id = bo.id
          left outer join review r on o.base_entity_id = r.base_project_id
     group by base_entity_id
) t
order by days_waiting desc;